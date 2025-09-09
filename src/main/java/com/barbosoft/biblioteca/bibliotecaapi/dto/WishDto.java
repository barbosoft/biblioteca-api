package com.barbosoft.biblioteca.bibliotecaapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * DTO usat al wire. Les dates van en epoch millis (Long) perquè l’app Android
 * les envia així. Al mapper es converteixen a LocalDateTime per l’Entity.
 */
public class WishDto {
    private Long id;
    private String titol;
    private String autor;
    private String isbn;
    private String sinopsis;
    private String notes;
    private String imatgeUrl;
    private BigDecimal preuDesitjat;

    // camps de sync / estat
    private Long createdAt;     // epoch millis (nullable)
    private Long updatedAt;     // epoch millis (nullable)
    @JsonProperty("isDeleted")
    private Boolean isDeleted;  // per batch sync
    private Long purchasedAt;   // epoch millis (nullable)

    public WishDto() {}

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitol() { return titol; }
    public void setTitol(String titol) { this.titol = titol; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getImatgeUrl() { return imatgeUrl; }
    public void setImatgeUrl(String imatgeUrl) { this.imatgeUrl = imatgeUrl; }

    public BigDecimal getPreuDesitjat() { return preuDesitjat; }
    public void setPreuDesitjat(BigDecimal preuDesitjat) { this.preuDesitjat = preuDesitjat; }

    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public Long getPurchasedAt() { return purchasedAt; }
    public void setPurchasedAt(Long purchasedAt) { this.purchasedAt = purchasedAt; }
}


