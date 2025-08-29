package com.barbosoft.biblioteca.bibliotecaapi.service;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.barbosoft.biblioteca.bibliotecaapi.service.metadata.BookMetadataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Enriquiment de llibres amb dades de diversos proveïdors.
 *
 * Política: per a cada camp, es conserva el valor del llibre base si NO és buit.
 * Si és buit/zero/null, es pren el primer valor no buit trobat entre els proveïdors
 * (en l’ordre en què s’injecten).
 */
@Service
public class BookEnrichmentService {

    private static final Logger log = LoggerFactory.getLogger(BookEnrichmentService.class);

    /**
     * L’ordre d’aquesta llista marca la preferència.
     * Ex.: [GoogleBooksProvider, OpenLibraryProvider, ...]
     */
    private final List<BookMetadataProvider> providers;

    public BookEnrichmentService(List<BookMetadataProvider> providers) {
        this.providers = providers;
    }

    /**
     * Enriquir només els camps buits del llibre base.
     * Retorna el mateix llibre si ISBN és buit o no hi ha aportacions.
     */
    public Llibre enrichMissing(Llibre base) {
        if (base == null) return null;

        final String isbn = base.getIsbn();
        if (isbn == null || isbn.isBlank()) {
            // Sense ISBN no podem fer consultes externes
            return base;
        }

        Llibre merged = base;

        for (BookMetadataProvider p : providers) {
            try {
                Llibre found = p.fetchByIsbn(isbn);
                if (found != null) {
                    merged = merge(merged, found);
                }
            } catch (Exception e) {
                // No volem interrompre la cadena per un proveïdor que falla
                log.warn("Provider {} va fallar enriquint ISBN {}: {}", p.getClass().getSimpleName(), isbn, e.getMessage());
            }
        }

        return merged;
    }

    /**
     * ‘b’ només sobreescriu ‘a’ quan el camp d’‘a’ és buit/null/zero.
     * IMPORTANT: es preserva l’ID d’‘a’.
     */
    private static Llibre merge(Llibre a, Llibre b) {
        Llibre r = new Llibre();

        // Identificador -> preservem el de 'a'
        r.setId(firstNonNull(a.getId(), b.getId()));

        // Identificadors / text
        r.setIsbn(firstNonBlank(a.getIsbn(), b.getIsbn()));
        r.setTitol(firstNonBlank(a.getTitol(), b.getTitol()));
        r.setAutor(firstNonBlank(a.getAutor(), b.getAutor()));
        r.setEditorial(firstNonBlank(a.getEditorial(), b.getEditorial()));
        r.setEdicio(firstNonBlank(a.getEdicio(), b.getEdicio()));
        r.setSinopsis(firstNonBlank(a.getSinopsis(), b.getSinopsis()));
        r.setImatgeUrl(firstNonBlank(a.getImatgeUrl(), b.getImatgeUrl()));
        r.setIdioma(firstNonBlank(a.getIdioma(), b.getIdioma()));
        r.setCategoria(firstNonBlank(a.getCategoria(), b.getCategoria()));
        r.setUbicacio(firstNonBlank(a.getUbicacio(), b.getUbicacio()));
        r.setComentari(firstNonBlank(a.getComentari(), b.getComentari()));

        // Numèrics (tractem 0 com a "desconegut" i per tant buit)
        r.setPagines(firstNonNullOrZero(a.getPagines(), b.getPagines()));
        r.setAnyPublicacio(firstNonNullOrZero(a.getAnyPublicacio(), b.getAnyPublicacio()));
        r.setPuntuacio(firstNonNullOrZero(a.getPuntuacio(), b.getPuntuacio()));

        // Booleans: considerem null com a "buit"
        r.setLlegit(firstNonNull(a.getLlegit(), b.getLlegit()));

        return r;
    }

    // ----------------- Helpers -----------------

    /** Retorna 'a' si no és null i no és en blanc; altrament 'b'. */
    private static String firstNonBlank(String a, String b) {
        return (a != null && !a.isBlank()) ? a : b;
    }

    /** Retorna 'a' si no és null; altrament 'b'. */
    private static <T> T firstNonNull(T a, T b) {
        return (a != null) ? a : b;
    }

    /**
     * Per enters on 0 es considera "valor desconegut".
     * Si 'a' és null o 0, usa 'b'; altrament 'a'.
     */
    private static Integer firstNonNullOrZero(Integer a, Integer b) {
        return (a != null && a != 0) ? a : b;
    }
}
