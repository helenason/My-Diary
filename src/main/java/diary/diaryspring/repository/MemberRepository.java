package diary.diaryspring.repository;

import diary.diaryspring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //메모리에(DB에) 회원 저장
    Optional<Member> findById(String id); // id로 회원 조회
    Optional<Member> findByName(String name); // 이름으로 회원 조회
    List<Member> findAll(); // 전체 회원 조회
}
