package ru.astinezh.categorycrud.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.ASTiNEZh.controller.CategoriesApi;
import ru.ASTiNEZh.dto.CategoryDTO;
import ru.astinezh.categorycrud.service.CategoryService;

import java.util.UUID;

@RestController
public class CategoryController implements CategoriesApi {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @PreAuthorize("hasRole('role_admin')")
    public ResponseEntity<Void> createCategory(@Valid CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('role_admin')")
    public ResponseEntity<CategoryDTO> deleteCategory(UUID uuid) {
        categoryService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('role_user')")
    public ResponseEntity<CategoryDTO> getCategory(UUID uuid) {
        return ResponseEntity.ok(categoryService.findById(uuid));
    }

    @Override
    @PreAuthorize("hasRole('role_admin')")
    public ResponseEntity<CategoryDTO> updateCategory(UUID uuid, @Valid CategoryDTO categoryDTO) {
        categoryService.update(uuid, categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
