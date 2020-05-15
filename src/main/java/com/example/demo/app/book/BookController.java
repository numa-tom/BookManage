package com.example.demo.app.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		model.addAttribute("title", "書籍登録画面");
		//model.addAttribute("bookForm", bookForm);
		return "manage/bookRegister";
	}
	@PostMapping("/register")
	public String registerGoBack(BookForm bookForm, Model model) {
		model.addAttribute("title", "書籍登録画面");
		//model.addAttribute("bookForm", bookForm);
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
		
		BookAll book = makeBook(bookForm);
		
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
	
	@GetMapping("/details")
	public String details(Model model) {
		model.addAttribute("title", "詳細ページ");
		return "manage/details";
	}
	
	private BookAll makeBook(BookForm bookForm) {
        BookAll bookAll = new BookAll();
        
        Book book = new Book();
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
}
