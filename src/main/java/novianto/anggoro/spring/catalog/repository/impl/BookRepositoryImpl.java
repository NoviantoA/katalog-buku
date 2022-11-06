package novianto.anggoro.spring.catalog.repository.impl;

import lombok.Data;
import novianto.anggoro.spring.catalog.domain.Book;
import novianto.anggoro.spring.catalog.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class BookRepositoryImpl {
//
//    private Map<Long, Book> bookMap;
//
//    @Override
//    public Book findById(Long id) {
//        Book book = bookMap.get(id);
//        return book;
//    }
//
//    @Override
//    public List<Book> findAll() {
//        List<Book> bookList = new ArrayList<>(bookMap.values());
//        return bookList;
//    }
//
//    @Override
//    public void save(Book book) {
//        // SIZE 2, MAKA DATA YANG DIPEROLEH 1 DAN 2
//        int size = bookMap.size();
//        book.setId((long) size+1);
//        bookMap.put(book.getId(), book);
//    }
//
//    @Override
//    public void update(Book book) {
//        bookMap.put(book.getId(), book);
//    }
//
//    @Override
//    public void delete(Long bookId) {
//        bookMap.remove(bookId);
//    }


}
