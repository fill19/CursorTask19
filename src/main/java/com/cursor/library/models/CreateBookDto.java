package com.cursor.library.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Used for book creation in some particular method.
 */
@Builder
public class CreateBookDto {

    private String name;
    private String description;
    private List<String> authors;
    private int yearOfPublication;
    private int numberOfWords;
    private int rating;

    public CreateBookDto() {
    }

    public CreateBookDto(String name, String description, List<String> authors, int yearOfPublication, int numberOfWords, int rating) {
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
        this.numberOfWords = numberOfWords;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "CreateBookDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authors=" + authors +
                ", yearOfPublication=" + yearOfPublication +
                ", numberOfWords=" + numberOfWords +
                ", rating=" + rating +
                '}';
    }
}
