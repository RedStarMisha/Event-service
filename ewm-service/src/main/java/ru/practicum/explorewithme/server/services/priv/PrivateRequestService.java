package ru.practicum.explorewithme.server.services.priv;

import ru.practicum.explorewithme.models.request.ParticipationRequestDto;
import ru.practicum.explorewithme.models.request.ParticipationRequestForSubscription;

import java.util.List;

public interface PrivateRequestService {
    List<ParticipationRequestDto> getEventRequestsByUser(long userId);

    ParticipationRequestDto addNewRequestByUser(long userId, long eventId);

    ParticipationRequestDto cancelUserRequest(long userId, long requestId);

    ParticipationRequestForSubscription addGroupToRequest(Long userId, Long requestId, Long group);

    ParticipationRequestForSubscription deleteGroupFromRequest(Long userId, Long requestId, Long groupId);
}
