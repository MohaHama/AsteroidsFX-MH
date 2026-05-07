package dk.sdu.se4.scoring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreController {

    private int score = 0;

    @PostMapping("/score/add/{points}")
    public int addScore(@PathVariable int points) {
        score = score + points;
        return score;
    }

    @GetMapping("/score")
    public int getScore() {
        return score;
    }

    @PostMapping("/score/reset")
    public int resetScore() {
        score = 0;
        return score;
    }
}