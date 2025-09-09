package com.barbosoft.biblioteca.bibliotecaapi.mapper;

import com.barbosoft.biblioteca.bibliotecaapi.dto.WishDto;
import com.barbosoft.biblioteca.bibliotecaapi.model.WishlistItem;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Converteix entre Entity (LocalDateTime) i DTO (epoch millis).
 */
public final class WishlistMapper {
    private WishlistMapper() {}

    /* ---------------- utilitats de dates ---------------- */

    private static LocalDateTime fromEpoch(Long ms) {
        if (ms == null) return null;
        return Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDateTime();
        // (si vols UTC, usa ZoneId.of("UTC"))
    }

    private static Long toEpoch(LocalDateTime dt) {
        if (dt == null) return null;
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /* ---------------- Entity -> DTO ---------------- */

    public static WishDto toDto(WishlistItem e) {
        if (e == null) return null;
        WishDto d = new WishDto();
        d.setId(e.getId());
        d.setTitol(e.getTitol());
        d.setAutor(e.getAutor());
        d.setIsbn(e.getIsbn());
        d.setSinopsis(e.getSinopsis());
        d.setNotes(e.getNotes());
        d.setImatgeUrl(e.getImatgeUrl());
        d.setPreuDesitjat(e.getPreuDesitjat());

        d.setCreatedAt(toEpoch(e.getCreatedAt()));
        d.setUpdatedAt(toEpoch(e.getUpdatedAt()));
        d.setPurchasedAt(toEpoch(e.getPurchasedAt()));
        // isDeleted no s’usa per entity “viva”; només en sincronització massiva
        d.setIsDeleted(false);
        return d;
    }

    public static List<WishDto> toDtoList(List<WishlistItem> entities) {
        return entities == null ? List.of()
                : entities.stream().map(WishlistMapper::toDto).collect(Collectors.toList());
    }

    /* ---------------- DTO -> Entity (INSERT) ---------------- */

    public static WishlistItem toEntity(WishDto d) {
        if (d == null) return null;
        WishlistItem e = new WishlistItem();

        // si ve id (update), el respectarem; si és null JPA en generarà un
        e.setId(d.getId());

        e.setTitol(d.getTitol());
        e.setAutor(d.getAutor());
        e.setIsbn(d.getIsbn());
        e.setSinopsis(d.getSinopsis());
        e.setNotes(d.getNotes());
        e.setImatgeUrl(d.getImatgeUrl());
        e.setPreuDesitjat(d.getPreuDesitjat());

        // Dates: si no venen definides, inicialitza createdAt a ara; updatedAt també
        e.setCreatedAt(fromEpoch(Objects.requireNonNullElseGet(d.getCreatedAt(), () -> System.currentTimeMillis())));
        e.setUpdatedAt(fromEpoch(Objects.requireNonNullElseGet(d.getUpdatedAt(), () -> System.currentTimeMillis())));
        e.setPurchasedAt(fromEpoch(d.getPurchasedAt()));
        return e;
    }

    /* ---------------- DTO -> Entity (UPDATE/UPSERT parcial) ---------------- */

    public static void fillFromDto(WishDto d, WishlistItem target) {
        if (d == null || target == null) return;

        if (d.getTitol() != null) target.setTitol(d.getTitol());
        if (d.getAutor() != null) target.setAutor(d.getAutor());
        if (d.getIsbn() != null) target.setIsbn(d.getIsbn());
        if (d.getSinopsis() != null) target.setSinopsis(d.getSinopsis());
        if (d.getNotes() != null) target.setNotes(d.getNotes());
        if (d.getImatgeUrl() != null) target.setImatgeUrl(d.getImatgeUrl());
        if (d.getPreuDesitjat() != null) target.setPreuDesitjat(d.getPreuDesitjat());

        if (d.getCreatedAt() != null)  target.setCreatedAt(fromEpoch(d.getCreatedAt()));
        if (d.getUpdatedAt() != null)  target.setUpdatedAt(fromEpoch(d.getUpdatedAt()));
        if (d.getPurchasedAt() != null) target.setPurchasedAt(fromEpoch(d.getPurchasedAt()));
    }

    /* ---------------- Llistes DTO -> Entity ---------------- */

    public static List<WishlistItem> toEntityList(List<WishDto> dtos) {
        return dtos == null ? List.of()
                : dtos.stream().map(WishlistMapper::toEntity).collect(Collectors.toList());
    }
}
