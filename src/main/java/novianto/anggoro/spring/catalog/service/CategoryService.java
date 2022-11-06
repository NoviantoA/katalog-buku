package novianto.anggoro.spring.catalog.service;

import novianto.anggoro.spring.catalog.domain.Category;
import novianto.anggoro.spring.catalog.dto.CategoryCreateAndUpdateRequestDTO;
import novianto.anggoro.spring.catalog.dto.CategoryListResponseDTO;
import novianto.anggoro.spring.catalog.dto.ResultPageResponseDTO;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    public void createAndUpdateCategory(CategoryCreateAndUpdateRequestDTO dto);

    public ResultPageResponseDTO<CategoryListResponseDTO> findCategoryList(Integer page, Integer limit, String sortBy, String direction, String categoryName);

    public List<Category> findCategories(List<String> categoryCodeList);

    public List<CategoryListResponseDTO> constructDTO(List<Category> categories);

    public Map<Long, List<String>> findCategoriesMap(List<Long> bookIdList);
}
