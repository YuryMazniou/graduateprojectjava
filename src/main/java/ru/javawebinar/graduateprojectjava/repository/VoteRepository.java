package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote,Integer> {
    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.user.id=:user_id AND v.time_create_vote=:today ")
    Vote getVoteToday(@Param("user_id") int user_id,@Param("today")LocalDate today);

    @Query("SELECT v FROM Vote v WHERE v.time_create_vote=:today ")
    List<Vote> getVotesToday(@Param("today")LocalDate today);

    @Override
    @Transactional
    Vote save(Vote entity);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:vote_id AND v.user.id=:user_id AND v.time_create_vote=:today")
    int delete(@Param("vote_id")int vote_id,@Param("user_id") int user_id, @Param("today")LocalDate today);
}
