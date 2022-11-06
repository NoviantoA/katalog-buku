package novianto.anggoro.spring.catalog.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookListResponseDTO implements Serializable {

    private String id;
    private String title;
    private String description;
    private String publisherName;

    private List<String> categoryCode;
    private List<String> authorName;
}
