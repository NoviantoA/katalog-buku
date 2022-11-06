package novianto.anggoro.spring.catalog.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorUpdateRequestDTO {

    private String authorName;
    private Long birthDate;

    private List<AddressUpdateRequestDTO> addresses;
}
