package novianto.anggoro.spring.catalog.service;

import novianto.anggoro.spring.catalog.dto.*;

import java.util.List;

public interface BookService {

    public BookDetailResponseDTO findBookDetailById(String bookId);
    public List<BookDetailResponseDTO> findBookListDetail();
    public void createNewBook(BookCreateRequestDTO dto);
    public void updateBook(Long bookId, BookUpdateRequestDTO dto);
    public void deleteBook(Long bookId);

    public ResultPageResponseDTO<BookListResponseDTO> findBookList(Integer page, Integer limit, String sortBy, String direction, String publisherName, String bookTitle, String authorName);

}
