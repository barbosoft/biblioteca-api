# Biblioteca‑API

API RESTful per la gestió d'una biblioteca: llibres, usuaris, préstecs, i llista de desitjos (wishlist).

##  Característiques principals

- Tecnologies: **Java**, **Spring Boot**, **Maven**
- Endpoints HTTP per operaciones **CRUD** (Create, Read, Update, Delete)
- Gestió de funcionalitats específiques:
  - Consulta de llibres per ISBN
  - Formes per comprar (llista de desitjos)
  - Gestió d’usuaris
  - Préstecs i devolucions

##  Requisits

- JDK 11 o superior
- Maven 3.6+
- (Opcional) Base de dades com **H2**, **PostgreSQL**, etc.

##  Instal·lació i Execució

1. *Clona el repositori*:
   ```bash
   git clone <URL-del-lloc.git>
   cd biblioteca-api

2. Build i execució local:
   ```bash
   mvn clean install
   mvn spring-boot:run

3. L'API estarà disponible a:
  ```bash
   http://localhost:8080/api/

4. Endpoints principals (exemples)
   ```bash 
   GET /api/books – Llista de llibres
   POST /api/wishlist – Afegeix un llibre a la llista de desitjos

5. Afegeix aquí més endpoints amb breus descripcions
   ```bash
  

6. Eines i dependències
   ```bash
   Spring Boot – inicialització ràpida
   Spring Data JPA – accés a dades
   H2 (opcional) – base de dades en memòria per desenvolupament
   Swagger / OpenAPI (opcional) – per documentar i testar l’API
