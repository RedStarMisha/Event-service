package ru.practicum.explorewithme.server.utils.mappers;

import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.category.NewCategoryDto;
import ru.practicum.explorewithme.server.models.Category;

public class CategoryMapper {
    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category(newCategoryDto.getName());
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }




}
