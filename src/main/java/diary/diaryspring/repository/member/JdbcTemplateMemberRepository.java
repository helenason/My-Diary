package diary.diaryspring.repository.member;

import diary.diaryspring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jt;

    public JdbcTemplateMemberRepository(DataSource ds) {
        this.jt = new JdbcTemplate(ds);
    }

    @Override
    public Member save(Member member) {

//        jt.update("INSERT INTO members(id, pw, name) VALUES(?, ?)",
//                member.getId(), member.getPw(), member.getName());

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jt);
        jdbcInsert.withTableName("members");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", member.getId());
        parameters.put("pw", member.getPw());
        parameters.put("name", member.getName());

        int executeKey = jdbcInsert.execute(new MapSqlParameterSource(parameters));
        System.out.println(executeKey);

        return member;
    }

    @Override
    public Optional<Member> findById(String id) {
        List<Member> result = jt
                .query("SELECT * FROM members WHERE id = ?", memberRowMapper(), id);
//        Member result = jt.queryForObject("SELECT * FROM members WHERE id = ?", memberRowMapper(), id);
//        return Optional.ofNullable(result);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jt.query("SELECT * FROM members WHERE name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jt.query("SELECT * FROM members", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() { // ResultSet -> Member()
        return (rs, rowNum) -> {
            Member member = new Member();

            member.setId(rs.getString("id"));
            member.setPw(rs.getString("pw"));
            member.setName(rs.getString("name"));

            return member;
        };
    }
}