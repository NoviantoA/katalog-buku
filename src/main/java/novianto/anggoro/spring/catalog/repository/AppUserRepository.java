package novianto.anggoro.spring.catalog.repository;

import novianto.anggoro.spring.catalog.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    public Optional<AppUser> findByUserName(String username);
}
