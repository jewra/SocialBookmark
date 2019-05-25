package vj.development.demo.web.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vj.development.demo.domain.AppUser;
import vj.development.demo.domain.Bookmark;
import vj.development.demo.repository.BookmarkRepository;
import vj.development.demo.repository.CategoryRepository;
import vj.development.demo.repository.UserRepository;
import vj.development.demo.service.BookmarkService;
import vj.development.demo.service.CategoryService;
import vj.development.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller
public class BookmarkController {

    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final BookmarkService bookmarkService;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CategoryService categoryService;

    public BookmarkController(UserService userService, CategoryRepository categoryRepository, BookmarkService bookmarkService, UserRepository userRepository, BookmarkRepository bookmarkRepository, CategoryService categoryService) {
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.bookmarkService = bookmarkService;
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/bookmark", method = RequestMethod.GET)
    public String newBookmarkFormView(Model model, HttpServletRequest request) {
        String ime = request.getUserPrincipal().getName();
        model.addAttribute("title", "Create New Bookmark");
        model.addAttribute("action", "/bookmark");
        model.addAttribute("categories", categoryService.getCategories(ime));
        model.addAttribute("bookmark", new Bookmark());
        return "bookmark/form";
    }

    @RequestMapping(value = "/bookmark/{id}/edit")
    public String editBookmarkView(Model model, @PathVariable("id") Long id, HttpServletRequest request) {
        String ime = request.getUserPrincipal().getName();
        model.addAttribute("title", "Bookmark Edit");
        model.addAttribute("action", "/bookmark");
        model.addAttribute("categories", categoryService.getCategories(ime));
        model.addAttribute("bookmark", bookmarkRepository.getOne(id));

        return "bookmark/form";
    }


    @RequestMapping(value = "/bookmark", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute("bookmark") Bookmark bookmark, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return "bookmark/form";
        }
        String ime = request.getUserPrincipal().getName();
        Optional<AppUser> user = userRepository.findAll().stream().filter(b -> b.getUsername().equals(ime)).findFirst();
        bookmark.setKorisnik(user.get());
        bookmarkService.saveBookmark(bookmark);

        return "redirect:/home/index";
    }

    @RequestMapping(value = "/bookmark/{id}/imageForm")
    public String imageFormView(Model model, @PathVariable("id") Long id) {

        model.addAttribute("bookmark", bookmarkRepository.getOne(id));

        return "bookmark/imageUploadForm";
    }

    @PostMapping("bookmark/{id}/image")
    public String handleImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        bookmarkService.saveImageFile(id, file);
        return "redirect:/home/index";
    }

    @GetMapping("bookmark/{id}/image")
    public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<Bookmark> bookmarkOptional = bookmarkRepository.findById(id);
        Bookmark bookmark = bookmarkOptional.get();
        if (bookmark.getImage() != null) {
            byte[] byteArray = new byte[bookmark.getImage().length];
            int i = 0;

            for (Byte wrappedByte : bookmark.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @RequestMapping(value = "/bookmark/{id}/delete")
    public String deleteBookmarkById(@PathVariable("id") Long id) {
        bookmarkRepository.deleteById(id);
        return "redirect:/";
    }

}
