package com.example.spring_practice.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.spring_practice.dto.BookDto;

/**
 * '@Service '가 적용되었는지,
 * 생성자 주입이 사용되었는지 확인합니다. -> 코드에서 확인
 * 존재하지 않는 ID로 상세 페이지를 요청할 때 404가 반환되는지 점검합니다.
 */
@SpringBootTest
public class BookServiceTest {
	@Autowired
	private BookService bookService;

	@Test
	@DisplayName("@Service가 적용되었는지")
	void serviceAnnotationTest() {
		assertThat(bookService).isNotNull();
	}

	@Test
	@DisplayName("존재하지 않는 ID로 상세 페이지를 요청할 때 404가 반환되는지 점검합니다.")
	void findNonExistentBookTest() {
		assertThatThrownBy(
			() -> {
				bookService.getBookById(0L);
			}
		)
			.isInstanceOf(ResponseStatusException.class)
			.hasMessageContaining(HttpStatus.NOT_FOUND.toString());
	}

	@Test
	@DisplayName("새로운 도서를 저장 후 반환값의 id가 부여되는지 확인합니다.")
	void registerNewBookTest() {
		BookDto newBook = new BookDto("book", "author", "description", "123456789");

		BookDto book = bookService.createBook(newBook);
		assertThat(book.getId()).isNotNull();
	}
}
