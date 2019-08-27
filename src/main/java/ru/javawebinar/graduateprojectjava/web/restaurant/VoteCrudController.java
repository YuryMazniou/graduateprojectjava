package ru.javawebinar.graduateprojectjava.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduateprojectjava.AuthorizedUser;
import ru.javawebinar.graduateprojectjava.model.Vote;
import ru.javawebinar.graduateprojectjava.service.RestaurantService;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = VoteCrudController.PROFILE_CRUD_VOTE, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteCrudController {
    public static final String PROFILE_CRUD_VOTE="/restaurants/profile/vote";

    private static final Logger log = getLogger(VoteCrudController.class);

    private RestaurantService restaurantService;

    @Autowired
    public VoteCrudController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PutMapping(value ="/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> saveUserVote(@PathVariable int restaurant_id,@AuthenticationPrincipal AuthorizedUser authUser){
        log.info("save user's vote {}",authUser.getId());
        Vote created = restaurantService.saveUserVote(restaurant_id,authUser.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(PROFILE_CRUD_VOTE + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{vote_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@PathVariable int vote_id,@AuthenticationPrincipal AuthorizedUser authUser){
        restaurantService.deleteVote(vote_id,authUser.getId());
    }

    @GetMapping
    public Vote getVoteToday(@AuthenticationPrincipal AuthorizedUser authUser){
        return restaurantService.getVoteToday(authUser.getId());
    }
}
