package ru.practicum.explorewithme.server.admin.category;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.category.NewCategoryDto;


@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {

    private final CategoryService service;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        return service.updateCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto categoryDto) {
        return service.addCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable(name = "catId") long catId) {
        service.deleteCategory(catId);
    }
}
