# ToDo projekt dla PGS

Projekt serwisu REST-owego na potrzeby rekrutacji.
Serwis obs�uguj�cy u�ytkownik�w i ich listy zada� do zrobienia.

## Funkcjonalno�ci:
- Dodawanie, usuwanie oraz edycja u�ytkownik�w.
- Zabezpieczenie ograniczaj�ce dost�p dla niezarejestrowanych u�ytkownik�w, oraz uniemo�liwiaj�ce dost�p do danych innych u�ytkownik�w dla tych ju� zalogowanych.
- Paginacja u�ytkownik�w dla admina.
- Walidacja danych przed dodaniem nowego u�ytkownika b�d� zadania
- Podstawowe testy jednostkowe sprawdzaj�ce poprawno�� dzia�ania serwis�w i kontroler�w.

## Uruchamianie
Aby uruchomi� serwis, nale�y: 
1. Pobra� kod �r�d�owy z repozytorium.
2. Uruchomi� konsol� cmd.exe.
3. Przej�� do folderu zawieraj�cego plik pom.xml.
4. Wpisa� komend�:
```
mvn spring-boot:run
```

## U�yte technologie:
- Java 8
- Spring Boot
- Spring Security
- Spring Data
- Maven
- HSQLDB