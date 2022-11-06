package novianto.anggoro.spring.catalog.repository;

import novianto.anggoro.spring.catalog.domain.Category;
import novianto.anggoro.spring.catalog.dto.CategoryQueryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    public Optional<Category> findByCode(String code);
    public Page<Category> findByNameLikeIgnoreCase(String categoryName, Pageable pageable);
    public List<Category> findByCodeIn(List<String> codes);
    // jpa projection
    @Query("select new novianto.anggoro.spring.catalog.dto.CategoryQueryDTO(b.id, bc.code) " +
            "from Book b join b.categories bc where b.id in :bookIdList")
    public List<CategoryQueryDTO> findCategoryByBookIdList(List<Long> bookIdList);
}
