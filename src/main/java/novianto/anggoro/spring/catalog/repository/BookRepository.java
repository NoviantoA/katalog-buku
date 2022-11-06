package novianto.anggoro.spring.catalog.repository;

import novianto.anggoro.spring.catalog.domain.Book;
import novianto.anggoro.spring.catalog.dto.BookQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findBookById(Long id);

    // select b from book b where b.secure_id = :id -> sql
    // select b FROM Book b Where b.secureId= :id -> jpql


    //SQL
    // SELECT b FROM book b INNER JOIN publisher p ON p.id = b.publisher_id WHERE p.name = :publisherName AND b.title = :bookTitle
    // JPQL
    @Query("SELECT DISTINCT new novianto.anggoro.spring.catalog.dto.BookQueryDTO(b.id, b.secureId, b.title, p.name, b.description) FROM Book b INNER JOIN Publisher p ON p.id = b.publisher.id JOIN b.authors ba " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :publisherName, '%')) AND LOWER(b.title) LIKE LOWER(CONCAT('%',:bookTitle)) " +
            "AND LOWER(ba.name) LIKE LOWER(CONCAT('%', :authorName, '%')) ")
    public Page<BookQueryDTO> findBookList(String bookTitle, String publisherName, String authorName, Pageable pageable);

    public Optional<Book> findBySecureId(String id);
//    public List<Book> findAll();
//    public void save(Book book);
//    public void update(Book book);
//    public void delete(Long bookId);

}
