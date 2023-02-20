package diary.diaryspring;

import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {
    private final MemoryMemberRepository mmr = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        mmr.clearStore();
    }

    // 회원 저장과 id로 조회
    @Test
    public void save_findById() {
        //given
        Member member1 = new Member();
        member1.setId("id1");
        member1.setPw("pw1");
        member1.setName("name1");

        //when
        mmr.save(member1);

        //then
        Member result = mmr.findById("id1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findByName() {
        //given
        Member member2 = new Member();
        member2.setId("id2");
        member2.setPw("pw2");
        member2.setName("name2");

        //when
        mmr.save(member2);

        //then
        Member result = mmr.findByName("name2").get();
        assertThat(result).isEqualTo(member2);
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
        mmr.save(member1);
        mmr.save(member2);

        //then
        List<Member> result = mmr.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}

