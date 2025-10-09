package com.example.spring_practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
1. com.example.springmvcpractice 경로 아래에 dto 패키지가 없다면 새로 만들고, 그 안에 BookDto 클래스를 생성합니다.
2. **필드(타입·이름)**: `Long id`, `String title`, `String author`, `String description`, `String isbn` 를 선언합니다.
3. **생성자**: 기본 생성자(파라미터 없음)와, 모든 필드를 받는 생성자를 각각 정의합니다.
4. **메서드**: 모든 필드에 대한 Getter/Setter를 작성합니다.2. **필드(타입·이름)**: `Long id`, `String title`, `String author`, `String description`, `String isbn` 를 선언합니다.
3. **생성자**: 기본 생성자(파라미터 없음)와, 모든 필드를 받는 생성자를 각각 정의합니다.
4. **메서드**: 모든 필드에 대한 Getter/Setter를 작성합니다.
 */

@NoArgsConstructor
@Getter
@Setter
public class BookDto {
	private Long id;

	private String title;
	private String author;
	private String description;
	private String isbn;

	/*
	id는 db가 할당해야하므로, id를 제외한 필드들만 받는 생성자를 정의합니다.
	 */
	public BookDto(String title, String author, String description, String isbn) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
	}
}
