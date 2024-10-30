package vn.han.services;

import java.util.List;

import org.springframework.data.domain.Page;

import vn.han.entities.Category;

public interface CategoryService {

	void deleteById(Long id);

	Category update(Category category);

	Category getById(Long id);

	Category add(Category category);

	List<Category> getByName(String name);

	List<Category> getAll();

	Page<Category> getAll(int pageNo, int pageSize);
	
	Page<Category> getByName(int pageNo, int pageSize, String name);
}
