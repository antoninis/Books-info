package org.test.backend.Entity;

import java.util.Objects;

public class Book {
    private Long id=null;
    private String name;
    private Author author;
    private Genre genre;
    private String publisher;
    private String year;
    private String city;

    public Book(){}

    public Book(Long id, String name, Author author, Genre genre, String publisher, String year, String city){
        this.id=id;
        this.name=name;
        this.author=author;
        this.genre=genre;
        this.publisher=publisher;
        this.year=year;
        this.city=city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(year, book.year) &&
                Objects.equals(city, book.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, genre, publisher, year, city);
    }
}
