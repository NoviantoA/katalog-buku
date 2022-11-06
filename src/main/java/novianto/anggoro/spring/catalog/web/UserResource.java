package novianto.anggoro.spring.catalog.web;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.dto.UserDetailResponseDTO;
import novianto.anggoro.spring.catalog.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserResource {

    private final AppUserService appUserService;

    @GetMapping("/v1/user")
    public ResponseEntity<UserDetailResponseDTO> findUserDetail(){
       return ResponseEntity.ok(appUserService.findUserDetail());
    }
}
