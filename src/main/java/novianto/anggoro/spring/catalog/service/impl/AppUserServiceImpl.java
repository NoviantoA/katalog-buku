package novianto.anggoro.spring.catalog.service.impl;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.dto.UserDetailResponseDTO;
import novianto.anggoro.spring.catalog.repository.AppUserRepository;
import novianto.anggoro.spring.catalog.service.AppUserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUserName(username). orElseThrow(() -> new UsernameNotFoundException("invalid username"));
    }

    @Override
    public UserDetailResponseDTO findUserDetail() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetailResponseDTO dto = new UserDetailResponseDTO();
        String username = ctx.getAuthentication().getName();
        dto.setUsername(username);
        return dto;
    }
}
