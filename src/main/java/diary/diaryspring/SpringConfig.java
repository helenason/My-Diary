package diary.diaryspring;

import diary.diaryspring.repository.MemberRepository;
import diary.diaryspring.repository.MemoryMemberRepository;
import diary.diaryspring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}