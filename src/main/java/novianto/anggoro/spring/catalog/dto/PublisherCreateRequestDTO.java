package novianto.anggoro.spring.catalog.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import novianto.anggoro.spring.catalog.annotation.LogThisArg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@LogThisArg
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublisherCreateRequestDTO implements Serializable {

    @NotBlank(message = "publisher.must.not.blank")
    private String publisherName;
    @NotBlank(message = "company_name.must.not.blank")
    private String companyName;
    private String address;
}
