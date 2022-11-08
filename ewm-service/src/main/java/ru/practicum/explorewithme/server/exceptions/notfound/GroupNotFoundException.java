package ru.practicum.explorewithme.server.exceptions.notfound;

public class GroupNotFoundException extends EntityNotFoundException {


    public GroupNotFoundException(String title) {
        super("Group", title);
    }

    @Override
    public String getEntityType() {
        return "Group";
    }
}
