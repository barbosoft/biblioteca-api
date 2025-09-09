package com.barbosoft.biblioteca.bibliotecaapi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titol;
    private String autor;
    private String isbn;

    @Column(length = 2000)
    private String sinopsis;

    @Column(length = 1000)
    private String notes;

    private String imatgeUrl;
    private String idioma;
    private Integer pagines;
    private String editorial;
    private String edicio;
    private Integer anyPublicacio;
    private BigDecimal preuDesitjat;

    // timestamps que l’app envia (en mil·lisegons)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // flags locals no calen al servidor, però si vols persistir-los:
    private Boolean pendingSync;
    private Boolean deleted;

    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;   // epoch millis

    // getters/setters/constructors…
    public WishlistItem() {}
    // (pots generar getters/setters amb Lombok si fas servir @Data)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getImatgeUrl() {
        return imatgeUrl;
    }

    public void setImatgeUrl(String imatgeUrl) {
        this.imatgeUrl = imatgeUrl;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getPagines() {
        return pagines;
    }

    public void setPagines(Integer pagines) {
        this.pagines = pagines;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getEdicio() {
        return edicio;
    }

    public void setEdicio(String edicio) {
        this.edicio = edicio;
    }

    public Integer getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(Integer anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getPendingSync() {
        return pendingSync;
    }

    public void setPendingSync(Boolean pendingSync) {
        this.pendingSync = pendingSync;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }
}
