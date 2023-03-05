package diary.diaryspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private String account;
    @GetMapping("/")
    public String home() {
        return("home");
    }
}
