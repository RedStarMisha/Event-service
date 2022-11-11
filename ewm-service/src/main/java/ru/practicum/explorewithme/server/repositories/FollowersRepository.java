package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.server.models.Follower;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowersRepository extends JpaRepository<Follower, Long> {
    Optional<Follower> findByPublisher_IdAndFollower_Id(long publisherId, long followerId);

    @Query("from Follower f where f.publisher=?1 and f.follower=?2 and f.group.title<>'FOLLOWER'")
    Optional<Follower> getFollowerWithStateFriendship(long publisherId, long followerId);

    @Query("from Follower f where f.follower.id=?1 and f.group.title<>'FOLLOWER'")
    List<Follower> findFollowingByUserIdWithStatusNotFollower(long userFollowerId, Pageable pageable);

    @Query("from Follower f where f.follower.id=?1 and f.group.title='FOLLOWER'")
    List<Follower> findFollowingWithStatusFollower(long userFollowerId, Pageable pageable);


    @Query("from Follower f where f.publisher.id=?1 and f.group.title<>'FOLLOWER'")
    List<Follower> findAllFollowersWithStatusFriend(long userFollowerId, Pageable pageable);

    @Query("from Follower f where f.publisher.id=?1 and f.group.id=?2")
    List<Follower> findFollowersWithGroup(long userFollowerId, long groupId, Pageable pageable);

    @Query("from Follower f where f.publisher.id=?1 and f.group.title='FOLLOWER'")
    List<Follower> findFollowersWithStatusFollower(long userFollowerId, Pageable pageable);

    Optional<Follower> findByRequest_Id(long subscriptionId);

    @Query("select count(f) from Follower f where f.publisher.id=?1 and f.group.title<>'FOLLOWER'")
    Long getFriendsAmount(long userId);

    @Query("select count(f) from Follower f where f.publisher.id=?1 and f.group.title='FOLLOWER'")
    Long getFollowersAmount(long userId);

    @Query("from Follower f where f.id=?1 and f.group.title<>'FOLLOWER'")
    Optional<Follower> findByIdAndGroupNotFollower(long followerId);

    @Query("from Follower f where f.request.id=?1 and (f.publisher.id=?2 or f.follower.id=?2)")
    Optional<Follower> findBySubscriptionIdAndUserId(long subscriptionId, long userId);

}
