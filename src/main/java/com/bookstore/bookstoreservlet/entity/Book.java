package com.bookstore.bookstoreservlet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 128, nullable = false)
    private String name;
    @Column(name = "author", length = 128, nullable = false)
    private String author;
    @Column(name = "description", length = 128, nullable = false)
    private String description;
    @Column(name = "stock", nullable = false)
    private int stock;
    @Column(name = "price", nullable = false)
    private Long price;
    @Column(name = "cover", nullable = true)
    private String cover;
    public Book(String name, String author, String description, Long price, int stock) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
    @Override
    public String toString() { return ("\nBook " + id + "\nName: " + name + "\nAuthor: " + author +
            "\nDescription: " + description + "\nStock: " + stock + "\nPrice: " + price); }
}
