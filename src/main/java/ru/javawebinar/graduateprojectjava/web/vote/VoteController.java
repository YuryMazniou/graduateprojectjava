package ru.javawebinar.graduateprojectjava.web.vote;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.graduateprojectjava.service.VoteService;

import static org.slf4j.LoggerFactory.getLogger;

public class VoteController {
    private static final Logger log = getLogger(VoteController.class);

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }
}
