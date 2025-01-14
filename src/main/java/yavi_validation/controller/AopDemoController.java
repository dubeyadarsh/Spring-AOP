package yavi_validation.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopDemoController {

	@GetMapping("/books")
    public List<String> getBooks() {
        return Arrays.asList("Book 1", "Book 2", "Book 3");
    }

    @PostMapping("/books")
    public String addBook(@RequestBody String book) {        
    	try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
		}
        return "Book added: " + book;
    }
}
