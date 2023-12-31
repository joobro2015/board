package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {


    @Autowired
    private  BoardService boardService;
    @GetMapping("/board/write")
    public String boardWriteForm(){
        //getmapping은 어떤 url로 지정해서 들어갈꺼냐
        return "boardwrite";
        //리턴값은 이 처리가 끝났을떄 보내준다.
        // 저렇게 해두면 그 페이지로 넘어간다.
    }

    @PostMapping("board/writepro")
    public String boardWritePro(Board board, Model model){

        boardService.write(board);


        model.addAttribute("message", "글작성이 완료 됐습니다.");
        model.addAttribute("Url", "/board/list");
        return"message";

    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){

      Page<Board> list = null;

        if(searchKeyword != null){
            list = boardService.boardSearchList(searchKeyword, pageable);
        }else {
            list = boardService.boardList(pageable);
        }


        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage-4, 1);
        int endPage = Math.min(nowPage+5, list.getTotalPages());


        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("list", list);
        //list라는 이름으로 boardService의 boardlist를 보낸다.

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id){


        model.addAttribute("board", boardService.boardView(id));
        return"boardview";

    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board", boardService.boardView(id));


        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board){

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp);


        return "redirect:/board/list";
    }

}
