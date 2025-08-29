package com.barbosoft.biblioteca.bibliotecaapi.service;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.barbosoft.biblioteca.bibliotecaapi.model.WishlistItem;
import com.barbosoft.biblioteca.bibliotecaapi.repository.LlibreRepository;
import com.barbosoft.biblioteca.bibliotecaapi.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final LlibreRepository llibreRepository;
    private final BookEnrichmentService bookEnrichmentService;

    public WishlistService(WishlistRepository wishlistRepository, LlibreRepository llibreRepository, BookEnrichmentService bookEnrichmentService) {
        this.wishlistRepository = wishlistRepository;
        this.llibreRepository = llibreRepository;
        this.bookEnrichmentService = bookEnrichmentService;
    }

    public WishlistItem add(WishlistItem item) {
        // Si només hi ha ISBN, podem enriquir una mica les dades
        if (item.getTitol() == null && item.getIsbn() !=null) {
            Llibre base = new Llibre(); base.setIsbn(item.getIsbn());
            Llibre enriched = bookEnrichmentService.enrichMissing(base);

            if (enriched.getTitol() != null) item.setTitol(enriched.getTitol());
            if (enriched.getAutor() != null) item.setAutor(enriched.getAutor());
            if (enriched.getImatgeUrl() != null) item.setImatgeUrl(enriched.getImatgeUrl());
            if (enriched.getSinopsis() != null) item.setSinopsis(enriched.getSinopsis());
        }

        return wishlistRepository.save(item);
    }

    @Transactional
    public Llibre purchase(Long id) {
        WishlistItem w = wishlistRepository.findByIsbn(String.valueOf(id))
                .orElseThrow(()  -> new IllegalArgumentException("No existeix wishlist id: " + id));

        // Si ja existeix el llibre per ISBN, retorna’l (i elimina de la wishlist)
        if (w.getIsbn() != null) {
            var existent = llibreRepository.findByIsbn(w.getIsbn());
            if (existent.isPresent()) {
                wishlistRepository.delete(w);
                return existent.get();
            }
        }

        // Converteix WishlistItem -> Llibre
        Llibre l = new Llibre();
        l.setTitol(w.getTitol());
        l.setAutor(w.getAutor());
        l.setIsbn(w.getIsbn());
        l.setImatgeUrl(w.getImatgeUrl());
        l.setSinopsis(w.getSinopsis());

        // Opcional: enriquir abans de desar
        l = bookEnrichmentService.enrichMissing(l);

        Llibre saved = llibreRepository.save(l);
        w.setPurchasedAt(LocalDateTime.now());
        wishlistRepository.delete(w); // o bé conserva’l amb purchasedAt != null
        return saved;
    }


}
