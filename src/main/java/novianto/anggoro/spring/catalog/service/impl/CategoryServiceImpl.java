package novianto.anggoro.spring.catalog.service.impl;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.domain.Category;
import novianto.anggoro.spring.catalog.dto.CategoryCreateAndUpdateRequestDTO;
import novianto.anggoro.spring.catalog.dto.CategoryListResponseDTO;
import novianto.anggoro.spring.catalog.dto.CategoryQueryDTO;
import novianto.anggoro.spring.catalog.dto.ResultPageResponseDTO;
import novianto.anggoro.spring.catalog.exception.BadRequestException;
import novianto.anggoro.spring.catalog.repository.CategoryRepository;
import novianto.anggoro.spring.catalog.service.CategoryService;
import novianto.anggoro.spring.catalog.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createAndUpdateCategory(CategoryCreateAndUpdateRequestDTO dto) {
        Category category = categoryRepository.findByCode(dto.getCode().toLowerCase()).orElse(new Category());
        if(category.getCode() ==  null){
            // buat category baru
            category.setCode(dto.getCode().toLowerCase());
        }
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        categoryRepository.save(category);
    }

    @Override
    public ResultPageResponseDTO<CategoryListResponseDTO> findCategoryList(Integer page, Integer limit, String sortBy, String direction, String categoryName) {
        // validasi category
        categoryName = StringUtils.isEmpty(categoryName) ? "%" : categoryName + "%";
        // make sort
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        // create pageable
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Category> pageRresult = categoryRepository.findByNameLikeIgnoreCase(categoryName, pageable);
        List<CategoryListResponseDTO> dtos = pageRresult.stream().map((c) -> {
            CategoryListResponseDTO dto = new CategoryListResponseDTO();
            dto.setCode(c.getCode());
            dto.setName(c.getName());
            dto.setDescription(c.getDescription());
            return dto;
        }).collect(Collectors.toList());
        return PaginationUtil.createResultPageDTO(dtos, pageRresult.getTotalElements(), pageRresult.getTotalPages());
    }

    @Override
    public List<Category> findCategories(List<String> categoryCodeList) {
        List<Category> categories = categoryRepository.findByCodeIn(categoryCodeList);
        if(categories.isEmpty()) throw new BadRequestException("category cant empty");
        return categories;
    }

    @Override
    public List<CategoryListResponseDTO> constructDTO(List<Category> categories) {
        return categories.stream().map((c) -> {
            CategoryListResponseDTO dto = new CategoryListResponseDTO();
            dto.setCode(c.getCode());
            dto.setName(c.getName());
            dto.setDescription(c.getDescription());
            return  dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<String>> findCategoriesMap(List<Long> bookIdList) {
        List<CategoryQueryDTO> queryList = categoryRepository.findCategoryByBookIdList(bookIdList);
        Map<Long, List<String >> categoryMaps = new HashMap<>();
        List<String> categoryCodeList = null;
        for (CategoryQueryDTO q:queryList){
            if (!categoryMaps.containsKey(q.getBookId())){
                categoryCodeList = new ArrayList<>();
            } else {
                categoryCodeList = categoryMaps.get(q.getBookId());
            }
            categoryCodeList.add(q.getCategoryCode());
            categoryMaps.put(q.getBookId(), categoryCodeList);
        }
        return categoryMaps;
    }
}
