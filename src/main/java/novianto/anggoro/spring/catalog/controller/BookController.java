package novianto.anggoro.spring.catalog.controller;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.dto.BookCreateRequestDTO;
import novianto.anggoro.spring.catalog.dto.BookDetailResponseDTO;
import novianto.anggoro.spring.catalog.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/list")
    public String findBookList(Model model){
        List<BookDetailResponseDTO> books = bookService.findBookListDetail();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/new")
    public String loadBookForm(Model model){
        BookCreateRequestDTO dto = new BookCreateRequestDTO();
        model.addAttribute("bookCreateDTO", dto);
        return "book/book-new";
    }

    @PostMapping("/new")
    public String addNewBook(@ModelAttribute("bookCreateDTO") @Valid BookCreateRequestDTO dto, BindingResult bindingResult, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("bookCreateDTO", dto);
            return  "book/book-new";
        }
        bookService.createNewBook(dto);
        return "redirect:/book/list";
    }
}
