package vj.development.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vj.development.demo.domain.Category;
import vj.development.demo.repository.CategoryRepository;

@Controller
public class CategoryController {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @RequestMapping("/category")
    public String categoryFormView(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("action", "/category");
        model.addAttribute("title", "New Category Form");
        
        return "category/form";
    }
    
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String categoryFormViewPost(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/home/index";
    }
}
