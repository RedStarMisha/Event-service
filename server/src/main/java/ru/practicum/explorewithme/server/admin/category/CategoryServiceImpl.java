package ru.practicum.explorewithme.server.admin.category;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.category.NewCategoryDto;
import ru.practicum.explorewithme.server.exceptions.notfound.CategoryNotFoundException;
import ru.practicum.explorewithme.server.repositories.CategoryRepository;

import static ru.practicum.explorewithme.server.utils.mappers.CategoryMapper.toCategory;
import static ru.practicum.explorewithme.server.utils.mappers.CategoryMapper.toDto;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = repository.findById(categoryDto.getId())
                .orElseThrow(() -> new CategoryNotFoundException(categoryDto.getId()));
        log.info("Update name category - {} on {}", category.getName(), categoryDto.getName());
        return toDto(repository.save(category));
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        log.info("Add new category - {}", newCategoryDto.getName());
        Category category = toCategory(newCategoryDto);
        return toDto(repository.save(category));
    }

    @Override
    public void deleteCategory(long catId) {
        repository.findById(catId).orElseThrow(() -> new CategoryNotFoundException(catId));
        log.info("Delete category with id = {}", catId);
        repository.deleteById(catId);
    }
}
