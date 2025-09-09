package com.barbosoft.biblioteca.bibliotecaapi.controller;

import com.barbosoft.biblioteca.bibliotecaapi.dto.WishDto;
import com.barbosoft.biblioteca.bibliotecaapi.mapper.WishlistMapper;
import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.barbosoft.biblioteca.bibliotecaapi.model.WishlistItem;
import com.barbosoft.biblioteca.bibliotecaapi.repository.WishlistRepository;
import com.barbosoft.biblioteca.bibliotecaapi.service.WishlistService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin // si ja tens CorsConfig global, això és opcional
public class WishlistController {

    private final WishlistRepository repo;
    private final WishlistService service;

    public WishlistController(WishlistRepository repo, WishlistService service) {
        this.repo = repo;
        this.service = service;
    }

    /* -------------------- LECTURA -------------------- */

    // GET /wishlist
    @GetMapping
    public List<WishDto> getAll() {
        return WishlistMapper.toDtoList(repo.findAll());
    }

    /* -------------------- UPSERT (1) -------------------- */

    // POST /wishlist/upsert   (un sol element)
    @PostMapping("/upsert")
    public WishDto upsert(@RequestBody WishDto dto) {
        WishlistItem entity = (dto.getId() != null)
                ? repo.findById(dto.getId()).orElseGet(WishlistItem::new)
                : new WishlistItem();

        WishlistMapper.fillFromDto(dto, entity);
        WishlistItem saved = repo.save(entity);
        return WishlistMapper.toDto(saved);
    }

    /* -------------------- UPSERT (BATCH) -------------------- */
    @Transactional
    @PostMapping("/upsertAll")
    public List<WishDto> upsertAll(@RequestBody List<WishDto> dtos) {
        var entities = WishlistMapper.toEntityList(dtos);
        var saved = repo.saveAll(entities);
        return WishlistMapper.toDtoList(saved);
    }

    /* -------------------- DELETE -------------------- */

    // DELETE /wishlist/{id}   (un sol element)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    // POST /wishlist/deleteMany   (varis elements)
    @PostMapping("/deleteMany")
    public void deleteMany(@RequestBody List<Long> ids) {
        repo.deleteAllById(ids);
    }

    /* -------------------- PURCHASE -------------------- */

    // POST /wishlist/purchase/{id}
    // Mou de wishlist -> llibres i elimina de wishlist
    @PostMapping("/purchase/{id}")
    public Llibre purchase(@PathVariable Long id) {
        return service.purchase(id);
    }
}
