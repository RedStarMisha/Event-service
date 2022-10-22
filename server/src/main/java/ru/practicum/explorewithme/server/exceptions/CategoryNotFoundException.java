package ru.practicum.explorewithme.server.exceptions;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Категория с id = " + id + " не найдена");
    }

    @Override
    public String getEntityType() {
        return "Category";
    }
}
