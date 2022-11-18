package ru.practicum.explorewithme.server.exceptions.notfound;

public class GroupNotFoundException extends EntityNotFoundException {


    public GroupNotFoundException(String title) {
        super("Group", title);
    }

    public GroupNotFoundException(long id) {
        super("Group", id);
    }

    @Override
    public String getEntityType() {
        return "Group";
    }
}
