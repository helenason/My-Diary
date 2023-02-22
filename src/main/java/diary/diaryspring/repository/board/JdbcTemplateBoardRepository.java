package diary.diaryspring.repository.board;

import diary.diaryspring.domain.Board;

import java.util.List;
import java.util.Optional;

public class JdbcTemplateBoardRepository implements BoardRepository {
    @Override
    public Board save(Board board) {
        return null;
    }

    @Override
    public Optional<Board> findByWriter(String writer) {
        return Optional.empty();
    }

    @Override
    public Optional<Board> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public List<Board> findAll() {
        return null;
    }
}
