package com.barbosoft.biblioteca.bibliotecaapi.controller;

import com.barbosoft.biblioteca.bibliotecaapi.model.WishlistItem;
import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.barbosoft.biblioteca.bibliotecaapi.repository.WishlistRepository;
import com.barbosoft.biblioteca.bibliotecaapi.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistRepository wishlistRepository;
    private final WishlistService wishlistService;

    public WishlistController(WishlistRepository wishlistRepository,
                              WishlistService wishlistService) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public List<WishlistItem> all() {
        return wishlistRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody WishlistItem item) {
        WishlistItem saved = wishlistService.add(item);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (!wishlistRepository.existsById(id)) return ResponseEntity.notFound().build();
        wishlistRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<?> purchase(@PathVariable Long id) {
        Llibre llibre = wishlistService.purchase(id);
        return ResponseEntity.ok(llibre);
    }
}

