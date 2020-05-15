package com.example.demo.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookAll;
import com.example.demo.repository.BookDao;

@Service
public class BookServiceImpl implements BookService {
	
	private final BookDao dao;
	
	@Autowired
	public BookServiceImpl(BookDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Book> findAllData() {
		return dao.findAllData();
	}
	
	@Override
	public Optional<Book> findDetailsById(int id){
		try {
			return dao.findDetailsById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new BookNotFoundException("指定された本が存在しません");
		}
	}

	@Override
	public void insert(BookAll book) {
		dao.insert(book);
	}

	@Override
	public int update(BookAll book) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
