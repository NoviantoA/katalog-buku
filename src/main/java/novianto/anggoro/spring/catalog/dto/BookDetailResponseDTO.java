package novianto.anggoro.spring.catalog.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookDetailResponseDTO implements Serializable {

    private static final long serialVersionUID = -7889938648939242355L;

    private String bookId;
    private List<AuthorResponseDTO> authors;
    private List<CategoryListResponseDTO> categories;
    private  PublisherResponseDTO publisher;
    private String bookTitle;
    private String bookDescription;


}
