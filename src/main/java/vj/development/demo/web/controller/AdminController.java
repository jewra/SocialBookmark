package vj.development.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vj.development.demo.domain.AppUser;
import vj.development.demo.domain.Bookmark;
import vj.development.demo.repository.CategoryRepository;
import vj.development.demo.service.BookmarkService;
import vj.development.demo.service.UserService;

@Controller
public class AdminController {
    
    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final CategoryRepository categoryRepository;
    
    
    public AdminController(UserService userService, BookmarkService bookmarkService, CategoryRepository categoryRepository) {
        this.userService = userService;
        this.bookmarkService = bookmarkService;
        this.categoryRepository = categoryRepository;
    }


    @RequestMapping(value = "/admin/index",method = RequestMethod.GET)
    public String adminIndexView (){
        return "admin/index";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getAdminUsersView(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("title", "Users Admin view");
        return "/admin/users";
    }
    
    @RequestMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
        return "redirect:/admin/users";
    }
    
    @RequestMapping(value = "/user/{userId}/edit")
    public String editUserView(Model model, @PathVariable("userId") Long userId) {
        model.addAttribute("title", "User Edit");
        model.addAttribute("action", "/user");
        model.addAttribute("user", userService.findOne(userId));
        return "user/form";
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String editUser(@ModelAttribute AppUser appUser) {
        userService.saveUser(appUser);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/bookmarks",method = RequestMethod.GET)
    public String adminBookmarksView (Model model){
        model.addAttribute("title", "Bookmarks Admin view");
        model.addAttribute("bookmarks",bookmarkService.getAllBookmarks());
        return "admin/bookmarks";
    }

    @RequestMapping(value = "/admin/bookmark/{bookmarkId}/delete")
    public String adminDeleteBookmark (@PathVariable("bookmarkId") long bookmarkId){
        bookmarkService.deleteBookmarkById(bookmarkId);
        return "redirect:/admin/bookmarks";
    }


    
    
}
