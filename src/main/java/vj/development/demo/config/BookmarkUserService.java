package vj.development.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vj.development.demo.domain.AppUser;
import vj.development.demo.domain.Role;
import vj.development.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class BookmarkUserService implements UserDetailsService
{
    
    private UserRepository userRepository;
    
    @Autowired
    public BookmarkUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUser appUser = userRepository.findByUsername(username);
            if (appUser == null) {
                return null;
            }
            return new  org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), getAuthorities(appUser));
        } catch (Exception e) {
            throw new UsernameNotFoundException("Korisnik nije nadjen");
        }
    }
    
    private Set<GrantedAuthority> getAuthorities(AppUser appUser){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role : appUser.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getType().toString());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}
