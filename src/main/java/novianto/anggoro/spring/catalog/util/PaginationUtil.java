package novianto.anggoro.spring.catalog.util;

import novianto.anggoro.spring.catalog.dto.ResultPageResponseDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtil {

    public static <T> ResultPageResponseDTO<T> createResultPageDTO(List<T> dtos, Long totalElements, Integer pages){
        ResultPageResponseDTO<T> result = new ResultPageResponseDTO<>();
        result.setPages(pages);
        result.setElements(totalElements);
        result.setResult(dtos);
        return result;
    }

    public static Sort.Direction getSortBy(String sortBy){
        if(sortBy.equalsIgnoreCase("asc")){
            return  Sort.Direction.ASC;
        }else {
            return Sort.Direction.DESC;
        }
    }
}
