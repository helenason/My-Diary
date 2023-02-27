package diary.diaryspring.service;

import diary.diaryspring.domain.Board;
import diary.diaryspring.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardService {

    @Autowired BoardRepository br;
    public Board write(Board board) {
        br.save(board);
        return board;
    }

    public void edit(int id, String newTitle, String newContent) {
        br.update(id, newTitle, newContent);
    }

    public void erase(Board board) {
        br.delete(board);
    }
}
