package novianto.anggoro.spring.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BookQueryDTO implements Serializable {

    private Long Id;
    private String bookId;
    private String bookTitle;
    private String publisherName;
    private String description;
}
