package com.example.demo.app.book;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookAll;
import com.example.demo.sevice.BookService;

@Controller
@RequestMapping("/book")//URL
public class BookController {
	
	private final BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping("/index")//URL
	public String index(BookForm bookForm, Model model,
			@ModelAttribute("complete") String complete) {
		
		List<Book> list = bookService.findAllData();
		model.addAttribute("list", list);
		model.addAttribute("title", "図書管理");
		return "manage/index";//ファイル名
	}
	
	@GetMapping("/register")
	public String register(BookForm bookForm, Model model) {
		
		bookForm.setNewBook(true);
		model.addAttribute("title", "書籍登録画面");
		model.addAttribute("bookForm", bookForm);
		return "manage/bookRegister";
	}
	@PostMapping("/register")
	public String registerGoBack(BookForm bookForm, Model model) {
		bookForm.setNewBook(true);
		model.addAttribute("title", "書籍登録画面");
		model.addAttribute("bookForm", bookForm);
		return "manage/bookRegister";
	}
	
	@PostMapping("/updateRegister")
	public String updateRegister(BookForm bookForm,@RequestParam("bookId") int bookId , 
			Model model) {
		bookForm.setNewBook(false);
		model.addAttribute("title", "内容変更画面");
		model.addAttribute("bookForm", bookForm);
		model.addAttribute("bookId", bookId);
		return "manage/bookRegister";
	}
	
	@PostMapping("/confirm")
	public String confirm(
			@Validated BookForm bookForm,
			BindingResult result, Model model) {
		if(result.hasErrors()) {
			//model.addAttribute("bookForm", bookForm);
			model.addAttribute("title", "確認エラー");
			
			return "manage/bookRegister";
		}
		model.addAttribute("title", "確認ページ");
		return "manage/confirm";
	}
	
	@PostMapping("/insert")
	public String insert(
			@Validated BookForm bookForm,
	        BindingResult result,
	        Model model, RedirectAttributes redirectAttributes) {
		
		BookAll book = makeBook(bookForm, 0);
		
		if(result.hasErrors()) {
			model.addAttribute("bookForm", bookForm);
	        List<Book> list = bookService.findAllData();
	        model.addAttribute("list", list);
	        model.addAttribute("title", "タスク一覧（バリデーション）");
	        return "manage/index";	
		}
		redirectAttributes.addFlashAttribute("complete", "登録完了");
		bookService.insert(book);
        return "redirect:/book/index";//url
	}
	
	@PostMapping("/update")
	public String update(
			@Validated BookForm bookForm,
			BindingResult result,
			@RequestParam("bookId") int bookId,
			Model model,
			RedirectAttributes redirectAttributes) {
		
		BookAll bookAll = makeBook(bookForm, bookId);
		
		if(result.hasErrors()) {
			model.addAttribute("bookForm", bookForm);
			model.addAttribute("title", "書籍管理");
			model.addAttribute("error", "更新できませんでした");
			return "book/index";
		}
		bookService.update(bookAll);
		redirectAttributes.addFlashAttribute("complete", "変更完了");
		return "redirect:/book/index";
		
	}
	
	@GetMapping("/details/{id}")
	public String showDetails(
			BookForm bookForm,
			@PathVariable int id,
			Model model) {
		//全情報を取得 Optionalラップ
		Optional<BookAll> bookOpt = bookService.findDetailsById(id);
		//BookFormに詰めなおす
		Optional<BookForm> bookFormOpt = bookOpt.map(b -> makeBookForm(b));
		//BookFormがNullでなければ取り出す
		if(bookFormOpt.isPresent()) {
			bookForm = bookFormOpt.get();
		}
		
		model.addAttribute("bookForm", bookForm);
		model.addAttribute("bookId", id);
		model.addAttribute("title", "詳細");
		
		return "manage/details";
	}
	
	@PostMapping("/delete")
	public String delete(
			@RequestParam("bookId") int id,
			Model model) {
		bookService.deleteById(id);
		return "redirect:/book/index";//url
		
	}
		
	
	
	private BookAll makeBook(BookForm bookForm, int bookId) {
        BookAll bookAll = new BookAll();
        
        Book book = new Book();
        if(bookId!=0) {
        	book.setId(bookId);
        }
        book.setName(bookForm.getName());
        book.setPlace(bookForm.getPlace());
        book.setType(bookForm.getType());
        
        bookAll.setBook(book);
        bookAll.setCharge(bookForm.getCharge());
        bookAll.setPageNumber(bookForm.getPageNumber());
        bookAll.setPublisher(bookForm.getPublisher());
        bookAll.setWriter(bookForm.getWriter());
        bookAll.setComment(bookForm.getComment());
        return bookAll;
    }
	
	
	private BookForm makeBookForm(BookAll book) {
		BookForm bookForm = new BookForm();
		
		bookForm.setName(book.getBook().getName());
		bookForm.setType(book.getBook().getType());
		bookForm.setPublisher(book.getPublisher());
		bookForm.setWriter(book.getWriter());
		bookForm.setCharge(book.getCharge());
		bookForm.setPageNumber(book.getPageNumber());
		bookForm.setPlace(book.getBook().getPlace());
		bookForm.setComment(book.getComment());
		
		return bookForm;
	}
}
