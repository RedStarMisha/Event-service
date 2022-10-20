package ru.practicum.explorewithme.clients.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.clients.model.CategoryDto;
import ru.practicum.explorewithme.clients.model.NewCategoryDto;

public class CategoryClient extends BaseClient {

    public CategoryClient(RestTemplate rest) {
        super(rest);
    }
    public ResponseEntity<Object> updateCategory(CategoryDto categoryDto) {
        return patch("", categoryDto);
    }

    public ResponseEntity<Object> addCategory(NewCategoryDto categoryDto) {
        return post("", categoryDto);
    }

    public ResponseEntity<Object> deleteCategory(long catId) {
        return delete("/" + catId);
    }

}
