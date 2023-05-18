package diary.diaryspring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private String account;
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Object logState = session.getAttribute("member");
        if (logState == null) {
            model.addAttribute("logState", 0);
        } else {
            model.addAttribute("logState", 1);
        }
        return("home");
    }
}
