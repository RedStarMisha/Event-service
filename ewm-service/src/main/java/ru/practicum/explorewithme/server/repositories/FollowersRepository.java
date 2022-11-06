package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.server.models.Follower;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowersRepository extends JpaRepository<Follower, Long> {
    Optional<Follower> findByPublisher_IdAndFollower_Id(long publisherId, long followerId);

    List<Follower> findAllByPublisher_IdAndGroupNotNull(long publisherId);

    List<Follower> findAllByPublisher_IdAndGroupIs(long publisherId, FriendshipGroup group);

    List<Follower> findAllByFollower_Id(long followerId);
    List<Follower> findAllByFollower_IdAndGroupNotNull(long followerId);



}
