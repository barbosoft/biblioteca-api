package com.barbosoft.biblioteca.bibliotecaapi.service.metadata;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.barbosoft.biblioteca.bibliotecaapi.service.GoogleBooksService;
import org.springframework.stereotype.Service;

@Service
public class GoogleBooksProvider implements BookMetadataProvider {
    private final GoogleBooksService delegate;
    public GoogleBooksProvider(GoogleBooksService delegate) { this.delegate = delegate; }

    @Override public Llibre fetchByIsbn(String isbn) { return delegate.fetchByIsbn(isbn); }
    @Override public String getName() { return "GoogleBooks"; }
}
