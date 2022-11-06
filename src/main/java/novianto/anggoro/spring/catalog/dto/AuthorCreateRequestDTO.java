package novianto.anggoro.spring.catalog.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import novianto.anggoro.spring.catalog.validator.annotation.ValidAuthorName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorCreateRequestDTO {

    @ValidAuthorName
    @NotBlank
    private String authorName;
    @NotNull
    private Long birthDate;

    @NotEmpty
    private List<AddressCreateRequestDTO> addresses;
}
