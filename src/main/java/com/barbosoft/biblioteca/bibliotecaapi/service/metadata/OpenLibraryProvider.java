package com.barbosoft.biblioteca.bibliotecaapi.service.metadata;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class OpenLibraryProvider implements BookMetadataProvider {

    private final RestTemplate http;
    private final ObjectMapper mapper;

    public OpenLibraryProvider(ObjectMapper mapper) {
        this.mapper = mapper;
        this.http = new RestTemplate();
        this.http.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public Llibre fetchByIsbn(String isbn) {
        try {
            String url = "https://openlibrary.org/api/books?format=json&jscmd=data&bibkeys=ISBN:" + isbn;
            String body = http.getForObject(url, String.class);
            if (body == null) return null;

            JsonNode root = mapper.readTree(body);
            JsonNode node = root.get("ISBN:" + isbn);
            if (node == null || node.isMissingNode()) return null;

            Llibre l = new Llibre();
            l.setIsbn(isbn);
            l.setTitol(optText(node, "title"));
            // Autors (agafem el primer nom si n’hi ha)
            JsonNode authors = node.get("authors");
            if (authors != null && authors.isArray() && authors.size() > 0) {
                JsonNode first = authors.get(0);
                l.setAutor(optText(first, "name"));
            }
            // Pàgines
            if (node.has("number_of_pages") && node.get("number_of_pages").isInt()) {
                l.setPagines(node.get("number_of_pages").asInt());
            }
            // Data publicació (la posem a edicio si arriba com a cadena)
            l.setEdicio(optText(node, "publish_date"));
            // Editorial (primer publisher)
            JsonNode publishers = node.get("publishers");
            if (publishers != null && publishers.isArray() && publishers.size() > 0) {
                l.setEditorial(optText(publishers.get(0), "name"));
            }
            // Imatge
            JsonNode cover = node.get("cover");
            if (cover != null) {
                String large = optText(cover, "large");
                String medium = optText(cover, "medium");
                String small = optText(cover, "small");
                l.setImatgeUrl(Optional.ofNullable(large).orElse(Optional.ofNullable(medium).orElse(small)));
            }
            // Idioma (quan disponible; sovint no ve)
            JsonNode languages = node.get("languages");
            if (languages != null && languages.isArray() && languages.size() > 0) {
                // Valors com { "key": "/languages/spa" } → “es” aproximat
                String key = optText(languages.get(0), "key"); // p.ex. /languages/spa
                if (key != null && key.contains("/")) {
                    String code = key.substring(key.lastIndexOf('/') + 1); // spa, eng, cat…
                    l.setIdioma(code);
                }
            }
            return l;
        } catch (Exception e) {
            return null; // silenci: si falla, l’agregador provarà altres fonts
        }
    }

    private static String optText(JsonNode node, String field) {
        return (node != null && node.has(field) && !node.get(field).isNull())
                ? node.get(field).asText()
                : null;
    }

    @Override public String getName() { return "OpenLibrary"; }
}
