package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookAll;

public interface BookDao {
	List<Book> findAllData();
	
	Optional<BookAll> findDetailsById(int id);
	
	void insert(BookAll book);
	
	int update(BookAll book);
	
	int deleteById(int id);
}
