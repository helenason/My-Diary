package diary.diaryspring.repository.board;

import diary.diaryspring.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board); // 메모리에(DB에) 글 저장
    Optional<Board> findByWriter(String writer); // 글쓴이로 글 조회
    Optional<Board> findByTitle(String title); // 제목으로 글 조회
    List<Board> findAll(); // 전체 글 조회
}
