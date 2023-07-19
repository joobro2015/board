package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    
    //글작성
    public void write(Board board){

        boardRepository.save(board);
//서비스의 write함수를 사용해서 레포지토리로저장하고 그 레포지토리가 엔티티로보내 데이터베이스에저장
    }

    
    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }


    //특정 게시물 불러오기
    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){

        boardRepository.deleteById(id);
    }

}
