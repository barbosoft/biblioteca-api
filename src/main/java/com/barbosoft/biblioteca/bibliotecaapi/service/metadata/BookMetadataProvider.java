package com.barbosoft.biblioteca.bibliotecaapi.service.metadata;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;

public interface BookMetadataProvider {
    /** Retorna un Llibre parcial (pot tenir molts camps null) o null si no troba res. */
    Llibre fetchByIsbn(String isbn);
    String getName();
}
