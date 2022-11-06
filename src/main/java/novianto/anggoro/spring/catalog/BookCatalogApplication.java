package novianto.anggoro.spring.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// mengaktifkan spring AOP
@EnableAspectJAutoProxy
@SpringBootApplication
public class BookCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCatalogApplication.class, args);

	}

}
