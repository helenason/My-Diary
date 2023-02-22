package diary.diaryspring.repository.member;

import diary.diaryspring.domain.Member;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<String, Member> storage = new HashMap<>();

    @Override
    public Member save(Member member) {
        storage.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return storage.values().stream()
                        .filter(mem -> mem.getName().equals(name))
                                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void clearStore() {
        storage.clear();
    }
}
