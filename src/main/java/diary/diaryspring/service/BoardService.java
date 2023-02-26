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

    public void edit(Board oldBoard, Board newBoard) {
        br.update(oldBoard, newBoard);
    }

    public void erase(Board board) {
        br.delete(board);
    }
}
