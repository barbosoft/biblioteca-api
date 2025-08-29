package com.barbosoft.biblioteca.bibliotecaapi.service;

import com.barbosoft.biblioteca.bibliotecaapi.model.Llibre;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Llibre fetchByIsbn(String isbn) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;

        System.out.println("Consulta a: " + url);

        try {
            //String json = restTemplate.getForObject(url, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Spring Boot Client");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String json = response.getBody();
            System.out.println("Resposta JSON: " + json);
            JsonNode root = objectMapper.readTree(json);

            if (root.path("totalItems").asInt() == 0) {
                return null; // No trobat
            }

            JsonNode volumeInfo = root.path("items").get(0).path("volumeInfo");

            Llibre llibre = new Llibre();
            llibre.setIsbn(isbn);
            llibre.setTitol(volumeInfo.path("title").asText(null));
            llibre.setAutor(volumeInfo.path("authors").isMissingNode() ? null : volumeInfo.path("authors").get(0).asText(null));
            llibre.setEditorial(volumeInfo.path("publisher").asText(null));
            llibre.setEdicio(volumeInfo.path("publishedDate").asText(null));
            llibre.setSinopsis(volumeInfo.path("description").asText(null));
            llibre.setPagines(volumeInfo.path("pageCount").asInt(0));
            llibre.setIdioma(volumeInfo.path("language").asText(null));
            llibre.setImatgeUrl(volumeInfo.path("imageLinks").path("thumbnail").asText(null));

            return llibre;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
