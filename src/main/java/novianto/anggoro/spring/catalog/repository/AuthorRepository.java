package novianto.anggoro.spring.catalog.repository;

import novianto.anggoro.spring.catalog.domain.Author;
import novianto.anggoro.spring.catalog.dto.AuthorQueryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // akses data dengan jpa melalui method name convention
    // find+keyword
    // sql-> select * from Author a where a.id= :authorId

    @Override
    public Optional<Author> findById(Long id);

    public List<Author> findBySecureIdIn(List<String> authorIdList);

    public Optional<Author> findBySecureId(String id);

    // query = where id = :id AND deleted=false
    public Optional<Author> findByIdAndDeletedFalse(Long id);

    // sql-> select a from Author a where a.name LIKE :authorName
    public List<Author> findByNameLike(String authorName);

    @Query("select new novianto.anggoro.spring.catalog.dto.AuthorQueryDTO(b.id, bc.name) " +
            "from Book b join b.authors bc where b.id in :bookIdList")
    public List<AuthorQueryDTO> findAuthorsByBookIdList(List<Long> bookIdList);
}
