package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.Vote;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote,Integer> {
    @Query("SELECT v FROM Vote v WHERE v.user.id=:user_id AND v.time_create_vote=:today ")
    Vote getVoteToday(@Param("user_id") int user_id,@Param("today")LocalDate today);
}
