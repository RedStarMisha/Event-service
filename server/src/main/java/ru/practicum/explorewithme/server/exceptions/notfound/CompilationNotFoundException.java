package ru.practicum.explorewithme.server.exceptions.notfound;

public class CompilationNotFoundException extends EntityNotFoundException {

    public CompilationNotFoundException(long id) {
        super("Compilation", id);
    }

    @Override
    public String getEntityType() {
        return "Compilation";
    }
}
