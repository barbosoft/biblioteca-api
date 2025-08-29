package com.barbosoft.biblioteca.bibliotecaapi.repository;

import com.barbosoft.biblioteca.bibliotecaapi.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    Optional<WishlistItem> findByIsbn(String isbn);
}
