package ru.practicum.explorewithme.server.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explorewithme.models.category.CategoryDto;
import ru.practicum.explorewithme.models.category.NewCategoryDto;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.event.EventFullDto;
import ru.practicum.explorewithme.models.event.EventShortDto;
import ru.practicum.explorewithme.models.event.Location;
import ru.practicum.explorewithme.models.subscription.SubscriptionRequestDto;
import ru.practicum.explorewithme.models.user.NewUserRequest;
import ru.practicum.explorewithme.models.user.UserDto;
import ru.practicum.explorewithme.models.user.UserShortDto;
import ru.practicum.explorewithme.models.user.UserWithSubscriptionDto;
import ru.practicum.explorewithme.server.models.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MyMapper {

    User toUser(NewUserRequest newUserRequest);
    UserDto toUserDto(User user);

    UserShortDto toUserShort(User user);

    UserWithSubscriptionDto toUserWithSubscriptionDto(User user);

    @Mapping(target = "friendship", source = "entity.friendshipRequest")
    SubscriptionRequestDto toSubscriptionDto(SubscriptionRequest entity);

    Category toCategory(NewCategoryDto newCategoryDto);

    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "lat", source = "entity.latitude")
    @Mapping(target = "lon", source = "entity.longitude")
    Location toLocationDto(Loc entity);

    @Mapping(target = "createdOn", source = "entity.created")
    @Mapping(target = "publishedOn", source = "entity.published")
    @Mapping(target = "requestModeration", source = "entity.moderation")
    @Mapping(target = "confirmedRequests", source = "entity.numberConfirmed")
    EventFullDto toEventFull(Event entity);

    @Mapping(target = "confirmedRequests", source = "entity.numberConfirmed")
    EventShortDto toEventShort(Event entity);

    List<EventShortDto> toMappedList(List<Event> list);

    CompilationDto toCompilationDto(Compilation compilation);
}
