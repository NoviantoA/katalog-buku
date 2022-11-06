package novianto.anggoro.spring.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthorQueryDTO implements Serializable {

    private Long bookId;
    private String authorName;
}
