package diary.diaryspring;

import diary.diaryspring.repository.BoardRepository;
import diary.diaryspring.repository.MemberRepository;
import diary.diaryspring.service.BoardService;
import diary.diaryspring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepository(dataSource);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService();
    }

    @Bean
    public BoardRepository boardRepository() {
        return new BoardRepository(dataSource);
    }

}