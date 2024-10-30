package vn.han.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import vn.han.entities.Category;
import vn.han.services.CategoryService;
import vn.han.utils.*;


@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping
	public String categories(Model model, String search, Integer pageNo) {
		Page<Category> page;
		Integer pageSize = 2;
		if (pageNo == null) pageNo = 0;
    	if(search != null && !search.isBlank())
    		page = categoryService.getByName(pageNo, pageSize, search);
		else
			page = categoryService.getAll(pageNo, pageSize);
        
        model.addAttribute("categories", page.getContent());
        model.addAttribute("search", search); 
        model.addAttribute("pageNo", page.getNumber());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("count", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("isFirst", page.isFirst());
		model.addAttribute("isLast", page.isLast());
		
		ViewMessage message = (ViewMessage) model.asMap().get("result");
		model.addAttribute("message", message);		 
        
        return "admin/categories";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		return "admin/add-category";
	}
	
	@PostMapping("/add")
	public String add(@Valid Category category, MultipartFile file, RedirectAttributes attributes, Model model) {
		try {
			if (!file.isEmpty()) {	
				String imageName = FileHandler.save(file);
				category.setImage(imageName);
			} else if (category.getImage() == null || category.getImage().isBlank()) {
				category.setImage(Constants.productImgDefault);
			}
			
            categoryService.add(category);
            attributes.addFlashAttribute("result", new ViewMessage("success", Constants.createSuccess));
            
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("result", new ViewMessage("danger", Constants.duplicateName));
        }
        catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("result", new ViewMessage("danger", Constants.failed));
        }
        return "redirect:/admin/categories";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Category cate = categoryService.getById(id);
		model.addAttribute("category", cate);
		return "admin/update-category";
	} 
	
	@PostMapping("/edit")
	public String edit(@Valid Category category, MultipartFile file, RedirectAttributes attributes, Model model) {
		try {
			if (categoryService.getById(category.getId()) == null) {
				attributes.addFlashAttribute("result", new ViewMessage("danger", Constants.itemNotFound));
				return "redirect:/admin/categories";
			}
			
			if (!file.isEmpty()) {	
				String imageName = FileHandler.save(file);
				category.setImage(imageName);
			}
			
            categoryService.add(category);
            attributes.addFlashAttribute("result", new ViewMessage("success", Constants.updateSuccess));
            
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("result", new ViewMessage("danger", Constants.duplicateName));
        }
        catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("result", new ViewMessage("danger", Constants.failed));
        }
        return "redirect:/admin/categories";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		try {
			categoryService.deleteById(id);
			attributes.addFlashAttribute("result", new ViewMessage("success", Constants.deleteSuccess));			
		} catch (Exception e) {
			e.printStackTrace();
            attributes.addFlashAttribute("result", new ViewMessage("danger", Constants.failed));
		}
		return "redirect:/admin/categories";
	}
}
