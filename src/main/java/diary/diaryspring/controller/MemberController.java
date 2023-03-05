package diary.diaryspring.controller;

import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.MemberRepository;
import diary.diaryspring.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired MemberRepository mr;
    @Autowired MemberService ms;

    private int pass;
    private boolean dupBtn;
    private boolean chkBtn;
    private String dupMsg;
    private String chkMsg;

    public static boolean isLogin;
    public static String account;

    @GetMapping("/join")
    public String joinForm(Model model){ // 새로고침(F5)
        pass = 0;
        dupBtn = true;
        chkBtn = true;
        model.addAttribute("member", new Member());
        model.addAttribute("dupBtn", true);
        model.addAttribute("chkBtn", true);
        return "members/join";
    }

    @PostMapping("/join")
    public String joinMember(Model model,
                             @RequestParam("button") String btn,
                             @RequestParam("check") String chkPw,
                             @ModelAttribute Member member) {

        model.addAttribute("pass", pass);
        model.addAttribute("dupBtn", dupBtn);
        model.addAttribute("chkBtn", chkBtn);

        if (btn.equals("중복확인")) { // ID 중복 확인 버튼

            boolean isDuplicated = ms.checkSameId(member.getId());
            if (isDuplicated) {
                dupMsg = "이미 존재하는 아이디입니다.";
            } else {
                pass++;
                dupBtn = false;
                dupMsg = "사용 가능한 아이디입니다.";
            }
            model.addAttribute("dupMsg", dupMsg);

        } else if (btn.equals("확인")) { // PW 확인 버튼

            if (!chkPw.equals(member.getPw())) {
                chkMsg = "다시 확인해주세요.";
            } else {
                pass++;
                chkBtn = false;
                chkMsg = "확인되었습니다.";
            }
            model.addAttribute("chkMsg", chkMsg);

        } else { // 가입 버튼
            pass = 0;
            ms.join(member);
            System.out.println("가입 성공");
            model.addAttribute("move", true);
        }
        model.addAttribute("pass", pass);
        model.addAttribute("dupBtn", dupBtn);
        model.addAttribute("chkBtn", chkBtn);
        return "members/join";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("member", new Member());
        return "members/login";
    }

    @PostMapping("/login")
    public String loginMember(Model model,
                              HttpServletRequest request,
                              @ModelAttribute Member member) {
        String msg = ms.login(member.getId(), member.getPw());
        model.addAttribute("message", msg);
        if (msg.contains("님 환영합니다.")) {
            // login session 설정
            Member loginMem = mr.findById(member.getId()).orElse(null);

            HttpSession session = request.getSession();
            session.setAttribute("member", loginMem);

            model.addAttribute("account", loginMem.getName());
        }
        return "members/login";
    }

    //logout 구현 (my page)
}

