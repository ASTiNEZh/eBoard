package ru.astinezh.categorycrud.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ASTiNEZh.dto.CategoryDTO;
import ru.astinezh.categorycrud.entity.Category;
import ru.astinezh.categorycrud.repository.CategoryRepository;

import java.util.Objects;
import java.util.UUID;

@Service
public class CategoryService {
    private final ModelMapper modelMapper = new ModelMapper();
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO findById(UUID uuid) {
        Category comment = categoryRepository.findById(uuid).orElse(null);
        if (Objects.nonNull(comment) && !comment.isDeleted())
            return modelMapper.map(comment, CategoryDTO.class);
        else
            return null;
    }

    public void create(CategoryDTO categoryDTO) {
        if (!categoryRepository.existsById(categoryDTO.getUuid()))
            categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
    }

    public void update(UUID uuid, CategoryDTO categoryDTO) {
        if (categoryRepository.findById(uuid).isPresent())
            categoryRepository.save(modelMapper.map(categoryDTO, Category.class));
    }

    public void delete(UUID uuid) {
        Category category = categoryRepository.findById(uuid).orElse(null);
        if (category != null) {
            category.setDeleted(true);
            categoryRepository.save(category);
        }
    }
}
