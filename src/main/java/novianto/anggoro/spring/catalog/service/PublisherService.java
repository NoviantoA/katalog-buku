package novianto.anggoro.spring.catalog.service;

import novianto.anggoro.spring.catalog.domain.Publisher;
import novianto.anggoro.spring.catalog.dto.*;

public interface PublisherService {

    public void createPublisher(PublisherCreateRequestDTO dto);
    public void updatePublisher(String publisherId, PublisherUpdateRequestDTO dto);
    public ResultPageResponseDTO<PublisherListResponseDTO> findPublisherList(Integer pages, Integer limit, String sortBy, String direction, String publisherName);
    // get data publiser
    public Publisher findPublisher(String publisherId);
    public PublisherResponseDTO constructDTO(Publisher publisher);
}
