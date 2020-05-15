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
	public Optional<Book> findDetailsById(int id){
		
		String sql = "SELECT id, name, type, place "
					+"FROM book where id == ?";
		
		Map<String, Object> result = jdbcTemplate.queryForMap(sql);
		
		Book book = new Book();
		book.setId((int)result.get("id"));
		book.setName((String)result.get("name"));
		book.setType((String)result.get("type"));
//		book.setWriter((String)result.get("writer"));
//		book.setPublisher((String)result.get("publisher"));
//		book.setPageNumber((int)result.get("pageNumber"));
//		book.setCharge((int)result.get("charge"));
		book.setPlace((String)result.get("place"));
//		book.setComment((String)result.get("comment"));
		
		Optional<Book> bookOpt = Optional.ofNullable(book);
		
		return bookOpt;
	}

	@Override
	public void insert(BookAll book) {
		String sql = "INSERT INTO book(name, type, writer, publisher, pageNumber, charge, place, comment) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, book.getBook().getName(), book.getBook().getType(), book.getWriter(), book.getPublisher(), book.getPageNumber(),
				book.getCharge(), book.getBook().getPlace(), book.getComment());
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
