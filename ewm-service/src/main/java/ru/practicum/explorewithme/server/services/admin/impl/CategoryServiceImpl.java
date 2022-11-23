package ru.practicum.explorewithme.server.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.category.NewCategoryDto;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.exceptions.requestcondition.RequestConditionException;
import ru.practicum.explorewithme.server.models.Category;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;
import ru.practicum.explorewithme.server.services.admin.CategoryService;
import ru.practicum.explorewithme.server.utils.mappers.MyMapper;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final MyMapper mapper;

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = repository.findById(categoryDto.getId())
                .orElseThrow(() -> new CategoryNotFoundException(categoryDto.getId()));
        category.setName(categoryDto.getName());
        log.info("Update name category - {} on {}", category.getName(), categoryDto.getName());
        return mapper.toCategoryDto(repository.save(category));
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        log.info("Add new category - {}", newCategoryDto.getName());
        Category category = mapper.toCategory(newCategoryDto);
        return mapper.toCategoryDto(repository.save(category));
    }

    @Override
    public void deleteCategory(long catId) {
        Category category = repository.findById(catId).orElseThrow(() -> new CategoryNotFoundException(catId));
        if (!category.getEvents().isEmpty()) {
            throw new RequestConditionException("Нельзя удалить категорию с которой связано событие");
        }
        log.info("Delete category with id = {}", catId);
        repository.deleteById(catId);
    }
}
