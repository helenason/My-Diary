package diary.diaryspring.controller;

import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.MemberRepository;
import diary.diaryspring.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired MemberService ms;
    @Autowired MemberRepository mr;
    @GetMapping("/myinfo")
    public String myInfoForm(Model model,
                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member account = (Member) session.getAttribute("member");

        model.addAttribute("member", account);

        return "mypage/myinfo";
    }

    @PostMapping("/myinfo")
    public String myInfo(Model model,
                         @RequestParam("pwNow") String pwNow,
                         @RequestParam("pwChange") String pwChange,
                         @RequestParam("pwChangeChk") String pwChangeChk,
                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member account = (Member) session.getAttribute("member");
        String message;

        if (pwNow.equals(account.getPw())) {
            if (pwChange.equals(pwChangeChk)) {
                ms.changePw(pwNow, pwChange);
                message = "변경 완료되었습니다.";
                session.setAttribute("member",
                        mr.findById(account.getId()).orElse(null));
            } else {
                message = "변경될 비밀번호가 다르게 입력되었습니다.";
            }
        } else {
            message = "현재 비밀번호가 올바르지 않습니다.";
        }


        model.addAttribute("member", account);
        model.addAttribute("msg", message);
        return "redirect:/";
    }
}
