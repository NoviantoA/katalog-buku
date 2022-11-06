package novianto.anggoro.spring.catalog.web;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.dto.AuthorCreateRequestDTO;
import novianto.anggoro.spring.catalog.dto.AuthorResponseDTO;
import novianto.anggoro.spring.catalog.dto.AuthorUpdateRequestDTO;
import novianto.anggoro.spring.catalog.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class AuthorResource {

    private final AuthorService authorService;

    // nyari author detail
    @GetMapping("/v1/author/{id}/detail")
    public ResponseEntity<AuthorResponseDTO> findAuthorById(@PathVariable String id){
        AuthorResponseDTO result = authorService.findAuthorById(id);
     return ResponseEntity.ok().body(result);
    }

    // menyimpan data
    @PostMapping("/v1/author")
    public ResponseEntity<Void> createNewAuthor(@RequestBody @Valid List<AuthorCreateRequestDTO> dto){
        authorService.createNewAuthor(dto);
        return ResponseEntity.created(URI.create("/author")).build();
    }

    @PutMapping("/v1/author/{authorId}")
    public ResponseEntity<Void> updateAuthor(@PathVariable String authorId,@RequestBody AuthorUpdateRequestDTO dto){
        authorService.updateAuthor(authorId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/author/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String authorId){
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok().build();
    }
}
