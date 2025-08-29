package com.barbosoft.biblioteca.bibliotecaapi.model;

import jakarta.persistence.*;

@Entity
public class Llibre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (length = 1024)
    private String titol;
    private String autor;
    private String editorial;
    private String edicio;
    @Column (unique = true)
    private String isbn;
    @Column (length = 2048)
    private String sinopsis;
    private Integer pagines;
    @Column (length = 1024)
    private String imatgeUrl;
    private Integer anyPublicacio;
    private String idioma;
    private String categoria;
    private String ubicacio;
    private Boolean llegit = false;
    private String comentari;
    private Integer puntuacio;    // 0..5

    // Getters & Setters


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

    public Integer getPagines() {
        return pagines;
    }

    public void setPagines(Integer pagines) {
        this.pagines = pagines;
    }

    public String getImatgeUrl() {
        return imatgeUrl;
    }

    public void setImatgeUrl(String imatgeUrl) {
        this.imatgeUrl = imatgeUrl;
    }

    public Integer getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(Integer anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public Boolean getLlegit() {
        return llegit;
    }

    public void setLlegit(Boolean llegit) {
        this.llegit = llegit;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    public Integer getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(Integer puntuacio) {
        this.puntuacio = puntuacio;
    }
}
