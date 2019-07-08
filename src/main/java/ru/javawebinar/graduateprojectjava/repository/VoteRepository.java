package ru.javawebinar.graduateprojectjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduateprojectjava.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote,Integer> {
}
