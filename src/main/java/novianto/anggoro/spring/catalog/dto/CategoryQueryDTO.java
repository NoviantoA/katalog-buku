package novianto.anggoro.spring.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class CategoryQueryDTO implements Serializable {

    private Long bookId;
    private String categoryCode;
}
