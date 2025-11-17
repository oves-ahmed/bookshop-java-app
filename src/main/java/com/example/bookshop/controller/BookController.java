package com.example.bookshop.controller;

import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class BookController {
    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", repo.findAll());
        return "index";
    }

    @GetMapping("/books/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "form";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        repo.save(book);
        return "redirect:/";
    }

    @GetMapping("/books/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Book book = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "form";
    }

    @PostMapping("/books/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        book.setId(id);
        repo.save(book);
        return "redirect:/";
    }

    @PostMapping("/books/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }
}
