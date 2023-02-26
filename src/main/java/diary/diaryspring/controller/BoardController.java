package diary.diaryspring.controller;

import diary.diaryspring.domain.Board;
import diary.diaryspring.repository.BoardRepository;
import diary.diaryspring.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired BoardService bs;
    @Autowired BoardRepository br;

    @GetMapping("/")
    public String boardForm(Model model) {
        List<Board> boardList = br.findAll();
        model.addAttribute("boardList", boardList);
        return "board/board";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/write";
    }

    @PostMapping("/write")
    public String writeBoard(Model model,
                           @ModelAttribute Board board,
                           @RequestParam("button") String btn) {
        if (btn.equals("게시")) {
            board.setWriter("수정예정");
            board.setDate();
            bs.write(board);
            System.out.println("게시 성공");
        }
        List<Board> boardList = br.findAll();
        model.addAttribute("boardList", boardList);
        return "board/board";
    }
}
