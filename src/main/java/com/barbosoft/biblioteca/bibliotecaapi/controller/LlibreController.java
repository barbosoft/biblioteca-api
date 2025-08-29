package com.barbosoft.biblioteca.bibliotecaapi.controller;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.barbosoft.biblioteca.bibliotecaapi.repository.LlibreRepository;
import com.barbosoft.biblioteca.bibliotecaapi.service.GoogleBooksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.barbosoft.biblioteca.bibliotecaapi.service.BookEnrichmentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/llibres")
public class LlibreController {

    private LlibreRepository llibreRepository;
    private final GoogleBooksService googleBooksService;
    private final BookEnrichmentService bookEnrichmentService;

    public LlibreController(LlibreRepository llibreRepository, GoogleBooksService googleBooksService, BookEnrichmentService bookEnrichmentService) {
        this.llibreRepository = llibreRepository;
        this.bookEnrichmentService = bookEnrichmentService;
        this.googleBooksService = googleBooksService;
    }

    // Obtenir tots els llibres de la base de dades
    @GetMapping
    public List<Llibre> getAll() {
        return llibreRepository.findAll();
    }

    /*
        // Afegir nou llibre a la base de dades
        @PostMapping
        public Llibre create(@RequestBody Llibre llibre) {
            return llibreRepository.save(llibre);
        }

     */
    // Afegir nou llibre a la base de dades, mitjançant Google Books
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Llibre llibre) {
        //Si ja existeix un llibre amb aquest ISBN, no el creem
        if (llibre.getIsbn() != null && !llibre.getIsbn().isBlank()) {
            //Optional<Llibre> existent = llibreRepository.findByIsbn(llibre.getIsbn());
            if (llibreRepository.findByIsbn(llibre.getIsbn()).isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Ja existeix un llibre amb aquest ISBN: " + llibre.getIsbn());
            }
        }

        // Completa amb Google Books, si cal
        boolean esLlibreBuit = (llibre.getTitol() == null || llibre.getTitol().isBlank());

        if (esLlibreBuit && llibre.getIsbn() != null && !llibre.getIsbn().isBlank()) {
            //Llibre llibreComplet = googleBooksService.fetchByIsbn(llibre.getIsbn());
            //Optional<Llibre> existent = llibreRepository.findByIsbn(llibre.getIsbn());
            //if (llibreComplet != null) {
            if (esLlibreBuit && llibre.getIsbn() != null && !llibre.getIsbn().isBlank()) {
                Llibre enriquit = bookEnrichmentService.enrichMissing(llibre);
                if (enriquit != null) {
                    llibre = enriquit;
                    /*
                    // Omplin només els camps que falten
                    if (llibre.getTitol() == null) llibre.setTitol(existent.getTitol());
                    if (llibre.getAutor() == null) llibre.setAutor(existent.getAutor());
                    if (llibre.getEditorial() == null) llibre.setEditorial(existent.getEditorial());
                    if (llibre.getEdicio() == null) llibre.setEdicio(existent.getEdicio());
                    if (llibre.getSinopsis() == null) llibre.setSinopsis(existent.getSinopsis());
                    if (llibre.getPagines() == null || llibre.getPagines() == 0)
                        llibre.setPagines(existent.getPagines());
                    if (llibre.getIdioma() == null) llibre.setIdioma(existent.getIdioma());
                    if (llibre.getImatgeUrl() == null) llibre.setImatgeUrl(existent.getImatgeUrl());

                     */
                }
            }

        }

        Llibre nou = llibreRepository.save(llibre);
        return ResponseEntity.status(HttpStatus.CREATED).body(nou);
    }

    /*
        // Actualitzar un llibre existent identificat per al seu ID
        @PutMapping
        public Llibre update(@PathVariable Long id, @RequestBody Llibre llibre) {
            llibre.setId(id);
            return llibreRepository.save(llibre);
        }

     */
// --- Actualitzar (PUT /api/llibres/{id}) ---
    @PutMapping("/{id}")
    public ResponseEntity<Llibre> update(@PathVariable Long id, @RequestBody Llibre body) {
        return llibreRepository.findById(id)
                .map(existing -> {
                    // actualització de camps (afegeix-hi els nous camps que vulguis)
                    existing.setTitol(body.getTitol());
                    existing.setAutor(body.getAutor());
                    existing.setIsbn(body.getIsbn());
                    existing.setEditorial(body.getEditorial());
                    existing.setEdicio(body.getEdicio());
                    existing.setSinopsis(body.getSinopsis());
                    existing.setPagines(body.getPagines());
                    existing.setIdioma(body.getIdioma());
                    existing.setImatgeUrl(body.getImatgeUrl());

                    // camps nous de l’app d’edició
                    existing.setLlegit(body.getLlegit());
                    existing.setComentari(body.getComentari());
                    existing.setPuntuacio(body.getPuntuacio());

                    return ResponseEntity.ok(llibreRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar llibre per al seu ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!llibreRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No s'ha trobat cap llibre amb ID: " + id);
        }
        llibreRepository.deleteById(id);
        return ResponseEntity.ok("Llibre eliminat correctament.");
    }


    // Cercar llibres per al codi ISBN exacte
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> findByIsbn(@PathVariable String isbn) {
        Optional<Llibre> resultats = llibreRepository.findByIsbn(isbn);
        if (resultats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No s'ha trobat cap llibre amb aquest ISBN: " + isbn);
        }
        return ResponseEntity.ok(resultats);
    }

    // Cercar llibres per categoria (parcial, insensible o majúscules
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> findByCategoria(@PathVariable String categoria) {
        List<Llibre> resultats = llibreRepository.findByCategoriaContainingIgnoreCase(categoria);
        if (resultats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No s'ha trobat cap llibre amb aquesta categoria: " + categoria);
        }
        return ResponseEntity.ok(resultats);
    }

    // Cercar llibres per autor (parcial, insensible o majúscules
    @GetMapping("/autor/{autor}")
    public ResponseEntity<?> findByAutor(@PathVariable String autor) {
        List<Llibre> resultats = llibreRepository.findByAutorContainingIgnoreCase(autor);

        if (resultats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No s'ha trobat cap llibre amb el nom de l'autor: " + autor);
        }
        return ResponseEntity.ok(resultats);
    }

    // Cercar llibres per idioma exacte ex: ca, es, en
    @GetMapping("/idioma/{idioma}")
    public ResponseEntity<?> findByIdioma(@PathVariable String idioma) {
        List<Llibre> resultats = llibreRepository.findByIdioma(idioma);
        if (resultats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No s'ha trobat cap llibre amb aquest idioma: " + idioma);
        }
        return ResponseEntity.ok(resultats);
    }

    // Consulta a Google Books per ISBN i retornar un llibre preomplert
    @GetMapping("/fetch/{isbn}")
    public ResponseEntity<?> fetchFromGoogle(@PathVariable String isbn) {
        /*
       // Llibre llibre = bookEnrichmentService.enrichMissing();
        //Llibre llibre= bookEnrichmentService.enrichMissing(llibre);
        if (llibre == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cap llibre trobat amb aquest ISBN: " + isbn);
        }
        return ResponseEntity.ok(llibre);
    }
*/
        if (isbn == null || isbn.isBlank()) {
            return ResponseEntity.badRequest().body("ISBN buit.");
        }

        // Base mínim amb l’ISBN
        Llibre base = new Llibre();
        base.setIsbn(isbn);

        // Enriquir amb els proveïdors (ordre de preferència al servei)
        Llibre enriched = bookEnrichmentService.enrichMissing(base);

        // Si no s’ha obtingut res rellevant, considera-ho NOT_FOUND
        boolean buit = (enriched.getTitol() == null || enriched.getTitol().isBlank())
                && (enriched.getAutor() == null || enriched.getAutor().isBlank())
                && (enriched.getImatgeUrl() == null || enriched.getImatgeUrl().isBlank())
                && (enriched.getPagines() == null || enriched.getPagines() == 0);

        if (buit) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cap llibre trobat amb aquest ISBN: " + isbn);
        }

        return ResponseEntity.ok(enriched);

    }
}
