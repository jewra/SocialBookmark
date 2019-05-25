package vj.development.demo.service;

import org.springframework.stereotype.Service;
import vj.development.demo.domain.AppUser;
import vj.development.demo.domain.Category;
import vj.development.demo.repository.BookmarkRepository;
import vj.development.demo.repository.CategoryRepository;
import vj.development.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(BookmarkRepository bookmarkRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getCategories(String username) {
        AppUser loggedInUser = userRepository.findByUsername(username);
        List<Category> categoryList = categoryRepository.findAll();
        if (loggedInUser.isUnderAge()) {
            List<Category> categoryListUnderAge = new ArrayList<>();

            for (Category cat : categoryList) {
                if (!cat.getName().equals("adultContent")) {
                    categoryListUnderAge.add(cat);
                }
            }
            return categoryListUnderAge;
        }
        return categoryList;
    }

    public Set<Category> getUnderAgeCategories(Set<Category> categoryList, String loggedInUserName){
        AppUser loggedInUser = userRepository.findByUsername(loggedInUserName);
        if (loggedInUser.isUnderAge()) {
            Set<Category> categoryListUnderAge = new HashSet<>();
            for (Category cat : categoryList) {
                if (!cat.getName().equals("adultContent")) {
                    categoryListUnderAge.add(cat);
                }
            }
            return categoryListUnderAge;
        }
        return categoryList;
    }
}
