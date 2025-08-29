package com.barbosoft.biblioteca.bibliotecaapi.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titol;
    private String autor;
    private String isbn;
    private String imatgeUrl;
    private String sinopsis;

    @Column(length = 2000)
    private String notes;

    private BigDecimal preuDesitjat;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime purchasedAt;

    // Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImatgeUrl() {
        return imatgeUrl;
    }

    public void setImatgeUrl(String imatgeUrl) {
        this.imatgeUrl = imatgeUrl;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getPreuDesitjat() {
        return preuDesitjat;
    }

    public void setPreuDesitjat(BigDecimal preuDesitjat) {
        this.preuDesitjat = preuDesitjat;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }
}
