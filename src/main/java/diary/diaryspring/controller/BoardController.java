package diary.diaryspring.controller;

import diary.diaryspring.domain.Board;
import diary.diaryspring.repository.BoardRepository;
import diary.diaryspring.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired BoardService bs;
    @Autowired BoardRepository br;

    @GetMapping("")
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

    @GetMapping("/post")
    public String postForm(Model model,
                           @RequestParam("id") int id) {

        Board findPost = br.findById(id).orElse(null);
        model.addAttribute("board", findPost);

        return "board/post";
    }
    @PostMapping("/post")
    public String postBoard(Model model,
                            @RequestParam("id") int id,
                            @RequestParam("button") String btn) {
        model.addAttribute("id", id);
        if (btn.equals("수정")) {
            return "board/edit";
        } else { // "삭제"
            Board erasePost = br.findById(id).orElse(null);
            bs.erase(erasePost);
            return "";
        }
    }

    @GetMapping("/post/edit")
    public String editFrom(Model model,
                           @RequestParam("id") int id) {
        Board findPost = br.findById(id).orElse(null);
        model.addAttribute("board", findPost);
        return "board/edit";
    }
    @PostMapping("/post/edit")
    public String editBoard(Model model,
                            @RequestParam("id") int id,
                            @ModelAttribute Board newBoard) {
        String newTitle = newBoard.getTitle();
        String newContent = newBoard.getContent();

        bs.edit(id, newTitle, newContent);

        Board findPost = br.findById(id).orElse(null);
        model.addAttribute("board", findPost);

        return "redirect:/board/post?id=" + Integer.toString(id);
    }
}
