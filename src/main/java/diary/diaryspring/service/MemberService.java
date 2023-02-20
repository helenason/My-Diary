package diary.diaryspring.service;

import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.MemberRepository;
import diary.diaryspring.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
public class MemberService {

    private final MemberRepository mr;

    public MemberService(MemberRepository mr) {
        this.mr = mr;
    }

    public String join(Member member) { // 회원가입
//        if (!checkSameId(member.getId())) {
            mr.save(member);
            return member.getId();
//        } else {
//            return "";
//        }
    }

    public boolean checkSameId(String id) { // 중복 ID 체크
        /**
        mr.findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원");
                });
         **/
        return mr.findById(id).isPresent();
    }

    public String login(String id, String pw) { // 로그인 (위치가 여기가 맞나..?)

        Optional<Member> exist = mr.findById(id);
        if (exist.isPresent()) {
            if (exist.get().getPw().equals(pw)) {
//                return "로그인 성공";
                return exist.get().getName() + "님 환영합니다.";
            } else {
                return "비밀번호 체크";
            }
        } else {
            return "없는 계정";
        }
    }
}
