package diary.diaryspring.board;

import diary.diaryspring.domain.Board;
import diary.diaryspring.repository.BoardRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class DbRepositoryTest {

    @Autowired BoardRepository br;

    @Test
    public void 글_저장_찾기() {
        Board board = new Board();
        board.setId(1);
        board.setTitle("기록의 힘");
        board.setWriter("얄루");
        board.setContent("오늘은 어쩌구");
        board.setDate();

        br.save(board);

        List<Board> result = br.findByWriter("얄루").orElse(null);
        if (result != null) {
            assertThat(result.get(0).getContent()).isEqualTo(board.getContent());
        }
    }

    @Test
    public void 전체글찾기() {
        Board board1 = new Board();
        board1.setId(123);
        board1.setTitle("기록의 힘");
        board1.setWriter("얄루");
        board1.setContent("오늘은 어쩌구");
        board1.setDate();


        Board board2 = new Board();
        board2.setId(1234);
        board2.setTitle("기록의 힘2");
        board2.setWriter("얄루2");
        board2.setContent("오늘은 어쩌구2");
        board2.setDate();


        br.save(board1);
        br.save(board2);

        List<Board> result = br.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
