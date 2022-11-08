package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;
import ru.practicum.explorewithme.server.models.SubscriptionRequest;
import ru.practicum.explorewithme.server.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionRequest, Long> {

    Optional<SubscriptionRequest> findByIdAndPublisher_IdAndStatus_Waiting(long subscriptionId, long publisherId);

    Optional<SubscriptionRequest> findByIdAndPublisher_IdAndStatusIs(long subscriptionId, long publisherId,
                                                                        SubscriptionStatus status);

    Optional<SubscriptionRequest> findByIdAndFollower_Id(long subscriptionId, long followerId);
    Optional<SubscriptionRequest> findByIdAndFollower_IdAndStatus_Waiting(long subscriptionId, long followerId);
    List<SubscriptionRequest> findAllByFollower_Id(long followerId, Pageable pageable);

    List<SubscriptionRequest> findAllByFollower_IdAndStatus(long followerId, SubscriptionStatus status, Pageable pageable);
    @Query("from SubscriptionRequest s where s.publisher.id=?1 and s.status<>2")
    List<SubscriptionRequest> findAllByPublisher(long publisherId, Pageable pageable);

    List<SubscriptionRequest> findAllByPublisher_IdAndStatusIs(long publisherId, SubscriptionStatus status, Pageable pageable);

    @Query("from SubscriptionRequest s where s.id=?1 and (s.publisher.id=?2 or  s.follower.id=?2)")
    Optional<SubscriptionRequest> findSubscription(long subscriptionId, long userId);

    Optional<SubscriptionRequest> findByFollower_IdAndPublisher_Id(long followerId, long publisherId);
    Optional<SubscriptionRequest> findByFollower_IdAndPublisher_IdAndStatusNot(long followerId, long publisherId,
                                                                               SubscriptionStatus status);






}
