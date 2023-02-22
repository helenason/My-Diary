package diary.diaryspring;

import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.member.MemberRepository;
import diary.diaryspring.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired MemberService ms;
    @Autowired MemberRepository mr;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member m1 = new Member();
        m1.setId("i1");
        m1.setPw("p1");
        m1.setName("n1");
        //when
        String resId = ms.join(m1);

        //then
        Member result = mr.findById(resId).get();
        assertEquals(m1.getName(), result.getName());
//        assertEquals(result, m1); //하면 오류가 뜬다.
    }

    @Test
    public void 중복회원() throws Exception {
        //when
        Member m1 = new Member();
        m1.setId("i1");
        m1.setPw("p1");
        m1.setName("n1");

        Member m2 = new Member();
        m2.setId("i1");
        m2.setPw("p2");
        m2.setName("n2");

        //given
        ms.join(m1);

        //then
        String result = ms.join(m2);
        assertThat(result).isEqualTo("");
    }

    @Test
    public void 정상로그인() throws Exception {
        //given
        Member member = new Member();
        member.setId("id");
        member.setPw("pw");
        member.setName("name");

        //when
        ms.join(member);
        String result = ms.login(member.getId(), member.getPw());

        //then
        assertThat(result).contains("님 환영합니다.");
    }

    @Test
    public void 틀린비번로그인() throws Exception {
        //given
        Member m = new Member();
        m.setId("id");
        m.setPw("pw");
        m.setName("name");

        //when
        ms.join(m);
        String result = ms.login(m.getId(), "other");

        //then
        assertThat(result).isEqualTo("비밀번호 체크");
    }

    @Test
    public void 없는계정로그인() throws Exception {
        //given
        Member m = new Member();
        m.setId("id");
        m.setPw("pw");
        m.setName("name");

        //when
        String result = ms.login(m.getId(), m.getPw());

        //then
        assertThat(result).isEqualTo("없는 계정");
    }
}
