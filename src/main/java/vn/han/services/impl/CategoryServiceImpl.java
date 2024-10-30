package vn.han.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.han.entities.Category;
import vn.han.repositories.CategoryRepository;
import vn.han.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repo;

	@Override
	public List<Category> getAll() {
		return repo.findAll();
	}
	
	@Override
	public List<Category> getByName(String name) {
		return repo.findByNameContainingIgnoreCase(name);
	}

	@Override
	public Category add(Category category) {
		return repo.save(category);
	}

	@Override
	public Category getById(Long id) {
		if (id == null)
			return null;
		return repo.findById(id).orElse(null);
	}

	@Override
	public Category update(Category category) {
		return repo.save(category);
	}

	@Override
	public void deleteById(Long id) {
		Category cate = repo.findById(id).orElse(null);
		if (cate != null) {
			repo.delete(cate);
		}
	}

	@Override
	public Page<Category> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return repo.findAll(pageable);
	}

	@Override
	public Page<Category> getByName(int pageNo, int pageSize, String name) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return repo.findByNameContainingIgnoreCase(pageable, name);
	}

}
