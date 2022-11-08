package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.server.models.Follower;
import ru.practicum.explorewithme.server.models.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowersRepository extends JpaRepository<Follower, Long> {
    Optional<Follower> findByPublisher_IdAndFollower_Id(long publisherId, long followerId);
    @Query("from Follower f where f.publisher=?1 and f.follower=?2 and f.group.title<>'FOLLOWER'")
    Optional<Follower> checkFriendship(long publisherId, long followerId);

    List<Follower> findAllByPublisher_IdAndGroupIs(long publisherId, Group group);

    @Query("from Follower f where f.follower.id=?1 and f.group.title<>'FOLLOWER'")
    List<Follower> findFollowing(long userId);
    @Query("from Follower f where f.follower.id=?1 and f.group.title='FOLLOWER'")
    List<Follower> findFollowers(long publisherId);

    List<Follower> findAllByFollower_Id(long followerId);
    List<Follower> findAllByFollower_IdAndGroupNotNull(long followerId);

    @Query("select count(f) from Follower f where f.publisher.id=?1 and f.group.title<>'FOLLOWER'")
    Long getFriendsAmount(long userId);
    @Query("select count(f) from Follower f where f.publisher.id=?1 and f.group.title='FOLLOWER'")
    Long getFollowersAmount(long userId);



}
