package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookAll;

@Repository
public class BookDaoImpl implements BookDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public BookDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Book> findAllData() {//index表示用に名前、ジャンル、配置をDBからもってくる
		String sql = "SELECT id, name, type, place FROM book";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Book> list = new ArrayList<Book>();
		
		for(Map<String, Object> result : resultList) {
			Book book = new Book();
			book.setId((int)result.get("id"));
			book.setName((String)result.get("name"));
			book.setType((String)result.get("type"));
			book.setPlace((String)result.get("place"));
			list.add(book);
		}		
	
		return list;
	}
	
	@Override
	public Optional<BookAll> findDetailsById(int id){
		
		String sql = "SELECT id, name, type, place, publisher, writer, charge, pageNumber, comment "
					+"FROM book WHERE id = ?";
		
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);
		
		//プライベートメソッドにして楽にする
		//BookAll bookAll = makeBookAll(result)とか
		Book book = new Book();
		BookAll bookAll = new BookAll();
		
		book.setId((int)result.get("id"));
		book.setName((String)result.get("name"));
		book.setType((String)result.get("type"));
		book.setPlace((String)result.get("place"));
		
		bookAll.setBook(book);
		bookAll.setWriter((String)result.get("writer"));
		bookAll.setPublisher((String)result.get("publisher"));
		bookAll.setPageNumber((int)result.get("pageNumber"));
		bookAll.setCharge((int)result.get("charge"));
		bookAll.setComment((String)result.get("comment"));
		
		Optional<BookAll> bookOpt = Optional.ofNullable(bookAll);
		
		return bookOpt;
	}

	@Override
	public void insert(BookAll book) {
		String sql = "INSERT INTO book( name, type, writer, publisher, pageNumber, charge, place, comment) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, book.getBook().getName(), book.getBook().getType(), book.getWriter(), book.getPublisher(), book.getPageNumber(),
				book.getCharge(), book.getBook().getPlace(), book.getComment());
	}

	@Override
	public int update(BookAll book) {
		String sql = "UPDATE book SET name=?, type=?, writer=?, publisher=?, pageNumber=?, charge=?, place=?, comment=? WHERE id = ?";
		return jdbcTemplate.update(sql, book.getBook().getName(), book.getBook().getType(), book.getWriter(), book.getPublisher(),
				book.getPageNumber(), book.getCharge(), book.getBook().getPlace(), book.getComment(),book.getBook().getId());
	}

	@Override
	public int deleteById(int id) {
		return jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
	}

}
