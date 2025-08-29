package com.barbosoft.biblioteca.bibliotecaapi.repository;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LlibreRepository extends JpaRepository<Llibre, Long>{
    // Mètodes personalitzats per fer les cerques

    // Cercar per ISBN (comprovació duplicats o cercar llibres concrets)
   // List<Llibre> findByIsbn(String isbn);

    // Cercar per categoria o gènere
    List<Llibre> findByCategoriaContaining(String categoria);

    // Cercar per categoria o gènere incomplert
    List<Llibre> findByCategoriaContainingIgnoreCase(String categoria);

    //Cercar per autor parcialment (útil per cercador)
    List<Llibre> findByAutorContainingIgnoreCase(String autor);

    //Cercar per idioma
    List<Llibre> findByIdioma(String idioma);

    // Mètode per comprovar duplicats ISBN
    Optional<Llibre> findByIsbn(String isbn);
}
