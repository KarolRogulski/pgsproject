# ToDo projekt dla PGS

Projekt serwisu REST-owego na potrzeby rekrutacji.
Serwis obs³uguj¹cy u¿ytkowników i ich listy zadañ do zrobienia.

## Funkcjonalnoœci:
- Dodawanie, usuwanie oraz edycja u¿ytkowników.
- Zabezpieczenie ograniczaj¹ce dostêp dla niezarejestrowanych u¿ytkowników, oraz uniemo¿liwiaj¹ce dostêp do danych innych u¿ytkowników dla tych ju¿ zalogowanych.
- Paginacja u¿ytkowników dla admina.
- Walidacja danych przed dodaniem nowego u¿ytkownika b¹dŸ zadania
- Podstawowe testy jednostkowe sprawdzaj¹ce poprawnoœæ dzia³ania serwisów i kontrolerów.

## Uruchamianie
Aby uruchomiæ serwis, nale¿y: 
1. Pobraæ kod Ÿród³owy z repozytorium.
2. Uruchomiæ konsolê cmd.exe.
3. Przejœæ do folderu zawieraj¹cego plik pom.xml.
4. Wpisaæ komendê:
```
mvn spring-boot:run
```

## U¿yte technologie:
- Java 8
- Spring Boot
- Spring Security
- Spring Data
- Maven
- HSQLDB