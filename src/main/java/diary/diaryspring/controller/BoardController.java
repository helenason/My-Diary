package diary.diaryspring.controller;

import diary.diaryspring.domain.Board;
import diary.diaryspring.domain.Member;
import diary.diaryspring.repository.BoardRepository;
import diary.diaryspring.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("")
    public String boardForm(HttpServletRequest request,
                            Model model) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            model.addAttribute("account", "익명");
        } else {
            model.addAttribute("account", member.getName());
        }

        List<Board> boardList = br.findAll();
        model.addAttribute("boardList", boardList);
        return "board/board";
    }
    @PostMapping("")
    public String searchBoard(HttpServletRequest request,
                              Model model,
                              @RequestParam("searchType") String type,
                              @RequestParam("search") String search) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("account", member.getName());

        List<Board> result;
        if (type.equals("제목")) {
            result = br.findByTitle(search).orElse(null);
        } else { // "글쓴이"
            result = br.findByWriter(search).orElse(null);
        }
        model.addAttribute("boardList", result);
        return "board/board";
    }

    @GetMapping("/write")
    public String writeForm(Model model,
                            HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            model.addAttribute("action", "글 작성 기능");
            return "noAccess";
        }
        model.addAttribute("account", member.getName());

        model.addAttribute("board", new Board());
        return "board/write";
    }

    @PostMapping("/write")
    public String writeBoard(Model model,
                           HttpServletRequest request,
                           @ModelAttribute Board board,
                           @RequestParam("button") String btn) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("account", member.getName());

        if (btn.equals("게시")) {
            board.setWriter(member.getName());
            board.setDate();

            bs.write(board);
//            System.out.println("게시 성공");
        }
        return "redirect:/board";
    }

    @GetMapping("/post")
    public String postForm(Model model,
                           @RequestParam("id") int id,
                           HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            model.addAttribute("action", "글 조회 기능");
            return "noAccess";
        }
        model.addAttribute("account", member.getName());

        Board findPost = br.findById(id).orElse(null);
        model.addAttribute("board", findPost);

        return "board/post";
    }
//    @PostMapping("/post")
//    public String postBoard(Model model,
//                            @RequestParam("id") int id,
//                            @RequestParam("button") String btn) {
//
//
//        model.addAttribute("id", id);
//        if (btn.equals("수정")) {
//            return "board/edit";
//        } else { // "삭제"
//            Board erasePost = br.findById(id).orElse(null);
//            bs.erase(erasePost);
//
//            List<Board> boardList = br.findAll();
//            model.addAttribute("boardList", boardList);
//            return "redirect:/board";
//        }
//    }

    @GetMapping("/post/edit")
    public String editFrom(Model model,
                           @RequestParam("id") int id,
                           HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            model.addAttribute("action", "글 수정 기능");
            return "noAccess";
        }
        model.addAttribute("account", member.getName());

        Board findPost = br.findById(id).orElse(null);
        model.addAttribute("board", findPost);
        return "board/edit";
    }
    @PostMapping("/post/edit")
    public String editBoard(Model model,
                            @RequestParam("id") int id,
                            @ModelAttribute Board newBoard,
                            HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("account", member.getName());

        String newTitle = newBoard.getTitle();
        String newContent = newBoard.getContent();

        bs.edit(id, newTitle, newContent);

        Board findPost = br.findById(id).orElse(null);
        model.addAttribute("board", findPost);

        return "redirect:/board/post?id=" + Integer.toString(id);
    }

    @GetMapping("/post/erase")
    public String eraseFrom(Model model,
                            HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            model.addAttribute("action", "글 삭제 기능");
            return "noAccess";
        }
        model.addAttribute("account", member.getName());

        return "board/erase";
    }
    @PostMapping("/post/erase")
    public String eraseBoard(Model model,
                             @RequestParam("id") int id,
                             @RequestParam("button") String btn,
                             HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("account", member.getName());

        if (btn.equals("YES")) {
            Board erasePost = br.findById(id).orElse(null);
            bs.erase(erasePost);
        }
        return "redirect:/board";

    }
}
