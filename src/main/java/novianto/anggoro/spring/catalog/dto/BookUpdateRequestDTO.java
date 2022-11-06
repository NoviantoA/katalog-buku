package novianto.anggoro.spring.catalog.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;

//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class BookUpdateRequestDTO {

    @NotBlank
    private String bookTitle;
    //@JsonProperty("synopsis")
    private String description;
}
