package vn.han.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.han.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByNameContainingIgnoreCase(String name);
	Page<Category> findByNameContainingIgnoreCase(Pageable page, String name);
}