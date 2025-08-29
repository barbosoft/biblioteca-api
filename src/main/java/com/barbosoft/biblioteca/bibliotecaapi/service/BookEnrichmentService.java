package com.barbosoft.biblioteca.bibliotecaapi.service.metadata;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookEnrichmentService {
    public Llibre enrichMissing(Llibre base) {
        // Exemple: prova proveïdors en cadena i ves fusionant només els camps buits
        Llibre merged = base;
        Llibre g = googleBooksProvider.fetchByIsbn(base.getIsbn());
        if (g != null) merged = merge(merged, g);
        Llibre o = openLibraryProvider.fetchByIsbn(base.getIsbn());
        if (o != null) merged = merge(merged, o);
        return merged;
    }

    /** ‘b’ només sobreescriu ‘a’ si ‘a’ és buit/null/zero. */
    private static Llibre merge(Llibre a, Llibre b) {
        Llibre r = new Llibre();
        r.setId(firstNonNull(a.getId(), b.getId()));
        r.setIsbn(firstNonBlank(a.getIsbn(), b.getIsbn()));
        r.setTitol(firstNonBlank(a.getTitol(), b.getTitol()));
        r.setAutor(firstNonBlank(a.getAutor(), b.getAutor()));
        r.setEditorial(firstNonBlank(a.getEditorial(), b.getEditorial()));
        r.setEdicio(firstNonBlank(a.getEdicio(), b.getEdicio()));
        r.setSinopsis(firstNonBlank(a.getSinopsis(), b.getSinopsis()));
        r.setPagines(firstNonNullOrZero(a.getPagines(), b.getPagines()));
        r.setImatgeUrl(firstNonBlank(a.getImatgeUrl(), b.getImatgeUrl()));
        r.setAnyPublicacio(firstNonNullOrZero(a.getAnyPublicacio(), b.getAnyPublicacio()));
        r.setIdioma(firstNonBlank(a.getIdioma(), b.getIdioma()));
        r.setCategoria(firstNonBlank(a.getCategoria(), b.getCategoria()));
        r.setUbicacio(firstNonBlank(a.getUbicacio(), b.getUbicacio()));
        r.setLlegit(firstNonNull(a.getLlegit(), b.getLlegit()));
        r.setComentari(firstNonBlank(a.getComentari(), b.getComentari()));
        r.setPuntuacio(firstNonNullOrZero(a.getPuntuacio(), b.getPuntuacio()));
        return r;
    }

    // ---------- Helpers ----------
    private static String firstNonBlank(String a, String b) {
        return (a != null && !a.isBlank()) ? a : b;
    }
    private static <T> T firstNonNull(T a, T b) {
        return (a != null) ? a : b;
    }
    private static Integer firstNonNullOrZero(Integer a, Integer b) {
        // si ‘a’ és null o 0, usa ‘b’
        return (a != null && a != 0) ? a : b;
    }
}
