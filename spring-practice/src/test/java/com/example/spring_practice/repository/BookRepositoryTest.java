package com.example.spring_practice.repository;

import com.example.spring_practice.dto.BookDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Repository가 적용되어 있는지,
 * 초기 데이터가 한 번만 추가되는지,
 * save, findById, findAll이 의도대로 동작하는지 확인합니다.
 * 목록의 표시 순서가 실행마다 바뀌지 않는지도 점검합니다.
 */

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("@Repository가 적용되어 있는지")
    void repositoryAnnotationTest() {
        assertThat(bookRepository).isNotNull();
    }

    @Test
    @DisplayName("초기 데이터가 한 번만 추가되는지")
    void initialRepositoryStateTest() {
        List<BookDto> books = bookRepository.findByAll();

        assertThat(books).hasSize(3);
    }

    @Test
    @DisplayName("save, findById가 의도대로 동작하는지")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void saveAndFindByIdTest() {
        BookDto newBook = new BookDto("book", "author", "description", "123456789");

		// 초기 3개의 책이 저장되어있으므로, 다음에 저장되는 책의 id는 4L이 된다.
		BookDto book = bookRepository.save(newBook);
        assertThat(book.getId()).isEqualTo(4L);

        Optional<BookDto> foundBook = bookRepository.findById(book.getId());
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("book");
    }

    @Test
    @DisplayName("findAll이 의도대로 동작하는지: 모든 책을 추가된 순서대로 반환해야 한다.")
    void findAllAndOrderTest() {
        List<BookDto> books = bookRepository.findByAll();

        assertThat(books).hasSize(3);
        assertThat(books)
                .extracting(BookDto::getTitle)
                .containsExactly("Clean Code", "객체지향의 사실과 오해", "Effective Java");
    }
}
