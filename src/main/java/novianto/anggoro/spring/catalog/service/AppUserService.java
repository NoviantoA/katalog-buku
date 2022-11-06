package novianto.anggoro.spring.catalog.service;

import novianto.anggoro.spring.catalog.dto.UserDetailResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    public UserDetailResponseDTO findUserDetail();
}
