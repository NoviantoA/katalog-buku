package novianto.anggoro.spring.catalog.web;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.annotation.LogThisMethod;
import novianto.anggoro.spring.catalog.dto.PublisherCreateRequestDTO;
import novianto.anggoro.spring.catalog.dto.PublisherListResponseDTO;
import novianto.anggoro.spring.catalog.dto.PublisherUpdateRequestDTO;
import novianto.anggoro.spring.catalog.dto.ResultPageResponseDTO;
import novianto.anggoro.spring.catalog.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.net.URI;

@Validated
@RestController
@AllArgsConstructor
public class PublisherResource {

    private final PublisherService publisherService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/publisher")
    public ResponseEntity<Void> createNewPublisher(@RequestBody @Valid PublisherCreateRequestDTO dto){
        publisherService.createPublisher(dto);
        return ResponseEntity.created(URI.create("/v1/publisher")).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/v1/publisher/{publisherId}")
    public ResponseEntity<Void> updatePublisher(@PathVariable @Size(max = 36, min = 36, message = "publisher.id.not.uuid") String publisherId, @RequestBody @Valid PublisherUpdateRequestDTO dto){
        publisherService.updatePublisher(publisherId, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @LogThisMethod
    @GetMapping("/v1/publisher")
    public ResponseEntity<ResultPageResponseDTO<PublisherListResponseDTO>> findPublisherList(
            @RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
            @RequestParam(name = "publisherName", required = false) String publisherName){
        return ResponseEntity.ok().body(publisherService.findPublisherList(pages, limit, sortBy, direction, publisherName));
    }
}
