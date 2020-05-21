package com.example.demo.sevice;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookAll;

public interface BookService {
	
	List<Book> findAllData();//全書籍の大まかな情報を持つ
	
	Optional<BookAll> findDetailsById(int id);//指定したidの詳細情報を持つ
	
	void insert(BookAll book);
	
	void update(BookAll book);
	
	void deleteById(int id);

}
