package vj.development.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vj.development.demo.domain.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    
    boolean existsByEmail( String email);
    boolean existsByUsername( String username);

    AppUser findByUsername(String username);
}
