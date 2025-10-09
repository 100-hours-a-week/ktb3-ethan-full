package com.example.spring_practice.service;

import java.awt.print.Book;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.spring_practice.dto.BookDto;
import com.example.spring_practice.repository.BookRepository;

/*
클래스에 @Service 어노테이션을 적용합니다.
 */
@Service
public class BookService {
	private final BookRepository bookRepository;

	/*
	생성자를 통해 BookRepository를 주입받습니다.
	 */
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/*
	저장소의 전체 목록을 반환합니다. (필요 시 id 오름차순 정렬)
	 */
	public List<BookDto> getAllBooks() {
		List<BookDto> list = bookRepository.findByAll();
		list.sort(
			(a, b) -> a.getId().compareTo(b.getId())
		);
		return list;
	}

	/*
	도서를 조회합니다. 존재하지 않을 경우 404로 응답되도록 처리합니다.
	(힌트: ResponseStatusException으로 HttpStatus.NOT_FOUND 전달)
	 */
	public BookDto getBookById(Long id) {
		return bookRepository.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	/*
	- 메서드명: `createBook`
	- 파라미터: `BookDto`
	- 동작: 저장소의 `save`를 호출하여 저장하고, 저장된 도서를 반환합니다.
	 */
	public BookDto createBook(BookDto bookDto) {
		return bookRepository.save(bookDto);
	}
}
