package novianto.anggoro.spring.catalog.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class BookCreateRequestDTO implements Serializable {

    @NotBlank
    private String bookTitle;
    @NotEmpty
    private List<String> authorIdList;
    @NotEmpty
    private List<String> categoryList;
    @NotBlank
    private String publisherId;
    //@JsonProperty("synopsis")
    private String description;
}
