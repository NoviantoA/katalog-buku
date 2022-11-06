package novianto.anggoro.spring.catalog.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import novianto.anggoro.spring.catalog.domain.Author;
import novianto.anggoro.spring.catalog.domain.Book;
import novianto.anggoro.spring.catalog.domain.Category;
import novianto.anggoro.spring.catalog.domain.Publisher;
import novianto.anggoro.spring.catalog.dto.*;
import novianto.anggoro.spring.catalog.exception.BadRequestException;
import novianto.anggoro.spring.catalog.repository.BookRepository;
import novianto.anggoro.spring.catalog.service.AuthorService;
import novianto.anggoro.spring.catalog.service.BookService;
import novianto.anggoro.spring.catalog.service.CategoryService;
import novianto.anggoro.spring.catalog.service.PublisherService;
import novianto.anggoro.spring.catalog.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service("bookService")
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

    // private final AuthorRepository authorRepository;

//    public BookServiceImpl() {
//        Author author = new Author();
//        book = new Book(author);
//    }

    // ubah menjadi constructor injection

    @Override
    public BookDetailResponseDTO findBookDetailById(String bookId) {
        log.info("====== start get data book =======");
       Book book =  bookRepository.findBySecureId(bookId).orElseThrow(() -> new BadRequestException("book_id invalid"));
        log.info("====== finish get data book =======");
       BookDetailResponseDTO dto = new BookDetailResponseDTO();
        dto.setBookId(book.getSecureId());
        log.info("===== start get data category =====");
        dto.setCategories(categoryService.constructDTO(book.getCategories()));
//        dto.setAuthorName(book.getAuthor().getName());
        log.info("===== finish get data category =====");
        log.info("===== start get data author =====");
        dto.setAuthors(authorService.constructDTO(book.getAuthors()));
        log.info("===== finish get data author =====");
        log.info("===== start get data publisher =====");
        dto.setPublisher(publisherService.constructDTO(book.getPublisher()));
        log.info("===== finish get data publisher =====");
        dto.setBookTitle(book.getTitle());
        dto.setBookDescription(book.getDescription());
       return dto;
    }

    @Override
    public List<BookDetailResponseDTO> findBookListDetail() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map((b) -> {
            BookDetailResponseDTO dto = new BookDetailResponseDTO();
            // set value book
//            dto.setAuthorName(b.getAuthor().getName());
            dto.setBookDescription(b.getDescription());
//            dto.setBookId(b.getId());
            dto.setBookTitle(b.getTitle());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createNewBook(BookCreateRequestDTO dto) {
        List<Author> authors = authorService.findAuthor(dto.getAuthorIdList());
//        Author author = new Author();
//        author.setName(dto.getAuthorName());
        List<Category> categories = categoryService.findCategories(dto.getCategoryList());
        Publisher publisher = publisherService.findPublisher(dto.getPublisherId());

        Book book = new Book();
        book.setAuthors(authors);
        book.setCategories(categories);
        book.setPublisher(publisher);
//        book.setAuthor(author);
        book.setTitle(dto.getBookTitle());
        book.setDescription(dto.getDescription());
        bookRepository.save(book);

    }

    @Override
    public void updateBook(Long bookId, BookUpdateRequestDTO dto) {
        // get book from repository
        Book book =  bookRepository.findBookById(bookId).orElseThrow(() -> new BadRequestException("book_id invalid"));
        // update
        book.setTitle(dto.getBookTitle());
        book.setDescription(dto.getDescription());
        // save
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public ResultPageResponseDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy, String direction, String publisherName, String bookTitle, String authorName) {
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<BookQueryDTO> pageResult = bookRepository.findBookList(bookTitle, publisherName, authorName, pageable);
        List<Long> idList = pageResult.stream().map(b -> b.getId()).collect(Collectors.toList());
        Map<Long, List<String>> categoryMap = categoryService.findCategoriesMap(idList);
        Map<Long, List<String>> authorMap = authorService.findAuthorMap(idList);

        List<BookListResponseDTO> dtos = pageResult.stream().map(b -> {
            BookListResponseDTO dto = new BookListResponseDTO();
            dto.setAuthorName(authorMap.get(b.getId()));
//            dto.setAuthorName(b.getAuthors().stream().map(a-> a.getName()).collect(Collectors.toList()));
            dto.setCategoryCode(categoryMap.get(b.getId()));
//            dto.setCategoryCode(b.getCategories().stream().map(c->c.getCode()).collect(Collectors.toList()));
            dto.setTitle(b.getBookTitle());
            dto.setPublisherName(b.getPublisherName());
            dto.setDescription(b.getDescription());
            dto.setId(b.getBookId());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
    }

//    public BookRepository getBookRepository() {
//        return bookRepository;
//    }
//
//    public void setBookRepository(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
}
