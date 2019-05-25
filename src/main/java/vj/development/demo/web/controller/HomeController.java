package vj.development.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vj.development.demo.domain.AppUser;
import vj.development.demo.domain.Bookmark;
import vj.development.demo.domain.Category;
import vj.development.demo.repository.CategoryRepository;
import vj.development.demo.service.BookmarkService;
import vj.development.demo.service.CategoryService;
import vj.development.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    
    public final UserService userService;
    public final CategoryRepository categoryRepository;
    public final BookmarkService bookmarkService;
    public final CategoryService categoryService;


    public HomeController(UserService userService, CategoryRepository categoryRepository, BookmarkService bookmarkService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.bookmarkService = bookmarkService;
        this.categoryService = categoryService;
    }

    @RequestMapping({"home/index", "", "/"})
    public String homeView(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();

        model.addAttribute("bookmarks", userService.findAllBookmarks(username));
        model.addAttribute("categories", categoryService.getCategories(username));
        model.addAttribute("showNewCategory", true);
        return "home/index";
    }
    
    @RequestMapping(value = "home/{categoryName}/category", method = RequestMethod.GET)
    public String homeViewByCategory(Model model, @PathVariable("categoryName") String categoryName, HttpServletRequest request) {
        Category foundCategory = categoryRepository.findByName(categoryName);
        String username = request.getUserPrincipal().getName();
        model.addAttribute("showNewCategory", false);
        model.addAttribute("bookmarks", bookmarkService.getBookmarksByCategory(foundCategory.getId()));
        model.addAttribute("categories", categoryService.getCategories(username));
        return "home/index";
    }
    
    @RequestMapping(value = "home/{username}/bookmarks", method = RequestMethod.GET)
    public String homeViewByUser(Model model, @PathVariable("username") String username, HttpServletRequest request) {
        AppUser loggedInUser = userService.findOne(request.getUserPrincipal().getName());
        AppUser foundUser = userService.findOne(username);

        Set<Bookmark> bookmarkSet = foundUser.getBookmarkList(loggedInUser);
        Set<Category> categorySet = new HashSet<>();
        bookmarkSet.stream().forEach(a -> categorySet.add(a.getKategorija()));
        model.addAttribute("showNewCategory", false);
        model.addAttribute("bookmarks", bookmarkSet);
        model.addAttribute("userCategories", categorySet);
        return "home/index";
    }
    
    @RequestMapping(value = "home/{username}/bookmarks/{category}", method = RequestMethod.GET)
    public String homeViewByUserAndCategory(Model model, @PathVariable("username") String username, @PathVariable("category") String category, HttpServletRequest request) {
        AppUser loggedInUser = userService.findOne(request.getUserPrincipal().getName());
        AppUser foundUser = userService.findOne(username);
        Set<Bookmark> bookmarkSet = foundUser.getBookmarkList();
        Set<Category> categorySet = new HashSet<>();

        bookmarkSet.stream().forEach(a -> categorySet.add(a.getKategorija()));
        Set<Bookmark> categoryBookmarks = new HashSet<>();
        
        for (Bookmark bookmark : bookmarkSet) {
            if(bookmark.getKategorija().getName().equals(category)){
                categoryBookmarks.add(bookmark);
            }
        }
        Set<Category> categories = categoryService.getUnderAgeCategories(categorySet,request.getUserPrincipal().getName());

        model.addAttribute("showNewCategory", false);
        model.addAttribute("bookmarks", categoryBookmarks);
        model.addAttribute("userCategories", categories);
        model.addAttribute("username",username);
        
        return "home/index";
    }
    
    
}
