package vj.development.demo.service;

import org.springframework.stereotype.Service;
import vj.development.demo.domain.Bookmark;
import vj.development.demo.domain.AppUser;
import vj.development.demo.domain.Role;
import vj.development.demo.repository.BookmarkRepository;
import vj.development.demo.repository.RoleRepository;
import vj.development.demo.repository.UserRepository;

import java.util.*;

@Service
public class UserService  {
    
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RoleRepository roleRepository;
    
    public UserService(UserRepository userRepository, BookmarkRepository bookmarkRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.roleRepository = roleRepository;
    }

    public AppUser saveUser(AppUser appUserToSave) {
        if(appUserToSave.getId()==null){
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.getOne(2l));
            appUserToSave.setRoles(roles);
        }
        
        return userRepository.save(appUserToSave);
    }
    
    public boolean emailInUse(String email){
        return  userRepository.existsByEmail(email);
    }
    public  boolean userExists (String username){
        return userRepository.existsByUsername(username);
    }
    public AppUser findOne(Long id) {
        Optional<AppUser> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new RuntimeException("AppUser Not Found!");
        }
        return foundUser.get();
    }
    
    public AppUser findOne(String username) {
        AppUser foundUser = userRepository.findByUsername(username);
        if (foundUser==null) {
            throw new RuntimeException("AppUser Not Found!");
        }
        return foundUser;
    }
    
    
    public Set<Bookmark> findBookmarksByUser(Long id) {
        
        Optional<AppUser> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new RuntimeException("AppUser Not Found!");
        }
        if (foundUser.get().getBookmarkList().isEmpty()) {
            throw new RuntimeException("AppUser Has No Bookmarks");
        }
        return foundUser.get().getBookmarkList();
    }
    
    
    public List<Bookmark> findAllBookmarks( String username) {
        AppUser loggedInUser = userRepository.findByUsername(username);
        List<Bookmark> bookmarkList = bookmarkRepository.findAll();
        if(loggedInUser.isUnderAge()){
            List<Bookmark>underAgeBookmarks = new ArrayList<>();
            for (Bookmark bm: bookmarkList) {
                if(!bm.getKategorija().getName().equals("adultContent")){
                    underAgeBookmarks.add(bm);
                }
            }
            return underAgeBookmarks;
        }
        return bookmarkList;
    }
    
    public void deleteUserById(Long userId) {
        Optional<AppUser> foundUser = userRepository.findById(userId);
        if (!foundUser.isPresent()) {
            throw new RuntimeException("AppUser Not Found!");
        }
        userRepository.deleteById(userId);
    }
    public List<AppUser> findAllUsers(){
        return userRepository.findAll();
    }
    
    
}
