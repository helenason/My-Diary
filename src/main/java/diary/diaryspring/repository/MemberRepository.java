package diary.diaryspring.repository;

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

public class MemberRepository {

    private final JdbcTemplate jt;

    public MemberRepository(DataSource ds) {
        this.jt = new JdbcTemplate(ds);
    }

    public Member save(Member member) {

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

    public Optional<Member> findById(String id) {
        List<Member> result = jt
                .query("SELECT * FROM members WHERE id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    public Optional<Member> findByName(String name) {
        List<Member> result = jt.query("SELECT * FROM members WHERE name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    public List<Member> findAll() {
        return jt.query("SELECT * FROM members", memberRowMapper());
    }

    public void updatePw(String from, String to) {
        jt.update("UPDATE members SET pw = ? WHERE pw = ?", to, from);
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