package com.bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    @GetMapping("/")
    public String home(Model model) {
        List<String> books = new ArrayList<>();
        books.add("The Lord of the Rings");
        books.add("The Hitchhiker's Guide to the Galaxy");
        books.add("Dune");
        books.add("1984");
        books.add("To Kill a Mockingbird");
        model.addAttribute("books", books);
        return "index";
    }
}