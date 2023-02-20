package diary.diaryspring.controller;

import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.MemoryMemberRepository;
import diary.diaryspring.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {

    MemoryMemberRepository mr = new MemoryMemberRepository();
    MemberService ms = new MemberService(mr);

    int pass;
    boolean dupBtn;
    boolean chkBtn;
    String dupMsg;
    String chkMsg;

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
    public String loginMember(Model model, @ModelAttribute Member member) {
        String m = ms.login(member.getId(), member.getPw());
        model.addAttribute("message", m);
        return "members/login";

    }
}

