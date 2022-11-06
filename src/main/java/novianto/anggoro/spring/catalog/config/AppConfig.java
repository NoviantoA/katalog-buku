package novianto.anggoro.spring.catalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import novianto.anggoro.spring.catalog.domain.Author;
import novianto.anggoro.spring.catalog.domain.Book;
import novianto.anggoro.spring.catalog.repository.BookRepository;
import novianto.anggoro.spring.catalog.repository.impl.BookRepositoryImpl;
import novianto.anggoro.spring.catalog.security.util.JWTTokenFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

// @ComponentScan(basePackages = ("novianto.belajar.spring"))
@Configuration
public class AppConfig {

//    @Bean
//    public Author author(){
//        return new Author(1L, "M. Novianto Anggoro", null, false);
//    }
//
//    @Bean
//    public Book book1(Author author){
//        // karena menggunakan properti base injection maka perlu mendefinisikan property base juga
//        Book book = new Book();
//        book.setId(1L);
//        book.setTitle("Belajar Spring");
//        book.setDescription("Belajar spring universe dengan saya M. Novianto Anggoro");
//        book.setAuthor(author);
//        return book;
//    }

//    @Bean
//    public Book book2(Author author){
//        // karena menggunakan properti base injection maka perlu mendefinisikan property base juga
//        Book book = new Book();
//        book.setId(2L);
//        book.setTitle("Belajar Java");
//        book.setDescription("Belajar Java dengan saya M. Novianto Anggoro");
//        book.setAuthor(author);
//        return book;
//    }
//
//    @Bean
//    public BookRepository bookRepository(Book book1, Book book2){
//        Map<Long, Book> bookMap = new HashMap<>();
//        bookMap.put(1L, book1);
//        bookMap.put(2L, book2);
//
//        BookRepositoryImpl bookRepository = new BookRepositoryImpl();
//        bookRepository.setBookMap(bookMap);
//
//        return bookRepository;
//    }

//    @Bean
//    public BookService bookService(BookRepository bookRepository){
//        return new BookServiceImpl(bookRepository);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public Key key(){
        byte[] keyBytes = Decoders.BASE64.decode("2f832yn98898594y5tjj6R6rfIKRGION84G7384hgeiqh8vnq8f38bjcfbwe23br8fqwkjB9Y9B9");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public JWTTokenFactory jwtTokenFactory(Key key){
        return new JWTTokenFactory(key);
    }

}
