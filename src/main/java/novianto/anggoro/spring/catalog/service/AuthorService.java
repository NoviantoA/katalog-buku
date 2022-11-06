package novianto.anggoro.spring.catalog.service;

import novianto.anggoro.spring.catalog.domain.Author;
import novianto.anggoro.spring.catalog.dto.AuthorCreateRequestDTO;
import novianto.anggoro.spring.catalog.dto.AuthorResponseDTO;
import novianto.anggoro.spring.catalog.dto.AuthorUpdateRequestDTO;

import java.util.List;
import java.util.Map;

public interface AuthorService {

    public AuthorResponseDTO findAuthorById(String id);
    public void createNewAuthor(List<AuthorCreateRequestDTO> dto);
    public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto);
    public void deleteAuthor(String authorId);
    public List<Author> findAuthor(List<String> authorIdList);
    public List<AuthorResponseDTO> constructDTO(List<Author> authors);
    public Map<Long, List<String>> findAuthorMap(List<Long> authorIdList);
}
