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
	public Optional<BookAll> findDetailsById(int id){
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
	public void update(BookAll book) {
		if(dao.update(book)==0) {
			throw new BookNotFoundException("更新する本情報がありません");
		}
	}

	@Override
	public void deleteById(int id) {
		if(dao.deleteById(id)==0) {
			throw new BookNotFoundException("削除する本情報が見当たりません");
		}
	}

}
