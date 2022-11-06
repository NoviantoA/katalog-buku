package novianto.anggoro.spring.catalog.dto;

import lombok.Data;
import novianto.anggoro.spring.catalog.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ErrorResponseDTO implements Serializable {

    private Date timestamp;
    private String message;
    private ErrorCode errorCode;
    private List<String> details;
    private HttpStatus status;

    public ErrorResponseDTO(String message, ErrorCode errorCode, List<String> details, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
        this.status = status;
        this.timestamp = new Date();
    }

    public static ErrorResponseDTO of(final String message, List<String> details, final ErrorCode errorCode, HttpStatus status){
     return new ErrorResponseDTO(message, errorCode, details, status);
    }
}
