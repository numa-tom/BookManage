package com.example.demo.app.book;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BookForm {
	
	//private int bookId;
	
	@NotNull(message="書籍名は？")
	private String name;
	
	@NotNull(message="ジャンルは？")
	private String type;
	
	@NotNull(message="誰が書いた？")
	private String publisher;
	
	
	@NotNull
	private String writer;
	
	@NotNull
	private int charge;
	
	@NotNull
	@Min(1)
	private int pageNumber;

	@NotNull
	private String place;
	
	private String comment;
	
	public boolean isNewBook;


	public BookForm() {
	}


//	public int getBookId() {
//		return bookId;
//	}
//
//
//	public void setBookId(int bookId) {
//		this.bookId = bookId;
//	}
//


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}



	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public int getCharge() {
		return charge;
	}


	public void setCharge(int charge) {
		this.charge = charge;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public boolean isNewBook() {
		return isNewBook;
	}


	public void setNewBook(boolean isNewBook) {
		this.isNewBook = isNewBook;
	}



	
}
