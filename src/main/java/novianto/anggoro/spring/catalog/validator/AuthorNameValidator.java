package novianto.anggoro.spring.catalog.validator;

import novianto.anggoro.spring.catalog.validator.annotation.ValidAuthorName;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AuthorNameValidator implements ConstraintValidator<ValidAuthorName, String> {

    @Override
    public boolean isValid(String authorName, ConstraintValidatorContext context) {
        return !authorName.equalsIgnoreCase("Novianto");
    }
}
