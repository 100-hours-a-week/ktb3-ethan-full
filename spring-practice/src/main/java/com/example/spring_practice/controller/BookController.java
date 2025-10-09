package com.example.spring_practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring_practice.dto.BookDto;
import com.example.spring_practice.service.BookService;

import lombok.RequiredArgsConstructor;

/*
클래스에 @Controller, @RequestMapping("/books") 적용
 */
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	/*
	GET /books 요청을 처리하고,
	모델에 books라는 이름으로 BookService.getAllBooks() 결과를 담아
	뷰 이름 books/list를 반환합니다.
	 */
	@GetMapping
	public String getBooks(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "books/list";
	}

	/*
	GET /books/{id} 요청을 처리하고,
	모델에 book이라는 이름으로 단건 조회 결과를 담아 **뷰 이름 books/detail*을 반환합니다.
	 */
	@GetMapping("/{id}")
	public String getBook(Model model, @PathVariable("id") Long id) {
		model.addAttribute("book", bookService.getBookById(id));
		return "books/detail";
	}

	/*
	1. **등록 폼 표시**
		- 경로/메서드: `GET /books/new`
		- 동작: 모델에 `bookDto`라는 이름으로 **빈 `BookDto`*를 추가하고, **뷰 이름 `books/form`*을 반환합니다.
	2. **등록 처리**
		- 경로/메서드: `POST /books`
		- 동작: 요청으로부터 `BookDto`를 바인딩 받아 `bookService.createBook(...)` 호출 후, **`redirect:/books`** 로 리다이렉트합니다.
		- 바인딩 시 `@ModelAttribute`를 활용합니다.
	 */
	@GetMapping("/new")
	public String showRegisterBookView(Model model) {
		model.addAttribute("bookDto", new BookDto());
		return "books/from";
	}
	@PostMapping
	public String registeredNewBook(@ModelAttribute BookDto bookDto) {
		bookService.createBook(bookDto);
		return "redirect:/books";
	}
}