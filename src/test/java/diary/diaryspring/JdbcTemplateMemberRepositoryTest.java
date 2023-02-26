package diary.diaryspring;

import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.MemberRepository;
import diary.diaryspring.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class JdbcTemplateMemberRepositoryTest {

    @Autowired MemberService ms;
    @Autowired MemberRepository jmr;


    // 회원 저장과 id로 조회
    @Test
    public void save_findById() {
        //given
        Member member1 = new Member();
        member1.setId("id1");
        member1.setPw("pw1");
        member1.setName("name1");

        //when
        jmr.save(member1);

        //then
        Member result = jmr.findById("id1").get();
        assertThat(result.getId()).isEqualTo(member1.getId());
//        assertThat(result).isEqualTo(member1); 하면 오류가 뜬다.
    }

    @Test
    public void findByName() {
        //given
        Member member2 = new Member();
        member2.setId("id2");
        member2.setPw("pw2");
        member2.setName("name2");

        //when
        jmr.save(member2);

        //then
        Member result = jmr.findByName("name2").get();
        assertThat(result.getPw()).isEqualTo(member2.getPw());
    }

    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setId("id1");
        member1.setPw("pw1");
        member1.setName("name1");

        Member member2 = new Member();
        member2.setId("id2");
        member2.setPw("pw2");
        member2.setName("name2");

        //when
        jmr.save(member1);
        jmr.save(member2);

        //then
        List<Member> result = jmr.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}

