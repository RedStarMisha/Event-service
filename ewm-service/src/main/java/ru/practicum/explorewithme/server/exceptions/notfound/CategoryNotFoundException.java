package ru.practicum.explorewithme.server.exceptions.notfound;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Category", id);
    }

    @Override
    public String getEntityType() {
        return "Category";
    }
}
