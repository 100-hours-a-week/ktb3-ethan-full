package com.example.spring_practice.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.spring_practice.dto.BookDto;

@Repository
public class BookRepository {
	private final Map<Long, BookDto> database;
	private long sequence;

	BookRepository() {
		this.database = new LinkedHashMap<>();
		this.sequence = 0;

		this.initializeData();
	}

	private void initializeData() {
		this.save(new BookDto("Clean Code", "Robert C. Martin", "소프트웨어 장인 정신을 담은 책입니다." , "9780132350884"));
		this.save(new BookDto("객체지향의 사실과 오해", "조영호" , "객체지향의 본질을 쉽게 설명합니다." , "9791186710770"));
		this.save(new BookDto("Effective Java" , "Joshua Bloch" , "자바 개발자를 위한 베스트 프랙티스 모음집입니다." , "9780134685991"));
	}

	/**
	 * save(BookDto book): `sequence`를 1 증가시켜 `id`를 부여하고 저장소에 넣은 뒤 저장된 객체를 반환합니다.
	 * @param bookDto 저장할 BookDto
	 */
	public BookDto save(BookDto bookDto) {
		bookDto.setId(++sequence);
		database.put(bookDto.getId(), bookDto);
		return bookDto;
	}

	/**
	 * findById(Long id): id로 저장소에서 BookDto를 조회합니다.
	 * @param id 조회할 BookDto의 id
	 */
	public Optional<BookDto> findById(Long id) {
		return Optional.ofNullable(database.get(id));
	}

	/**
	 * findAll(): 저장소의 모든 값을 List<BookDto>로 반환합니다.
	 */
	public List<BookDto> findByAll() {
		return new ArrayList<>(database.values());
	}
}