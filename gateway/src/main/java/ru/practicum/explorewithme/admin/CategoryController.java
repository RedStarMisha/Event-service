package ru.practicum.explorewithme.admin;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.admin.CategoryClient;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.category.NewCategoryDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class CategoryController {

    private final CategoryClient client;

    @PatchMapping
    public ResponseEntity<Object> updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return client.updateCategory(categoryDto);
    }

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody @Valid NewCategoryDto categoryDto) {
        return client.addCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(name = "catId")
                                                     //@Positive(message = "Id категории должно быть положительное")
                                                     Long catId) {
        return client.deleteCategory(catId);
    }
}
