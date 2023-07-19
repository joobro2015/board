package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}

//jpa를 사용해서 엔티티에 저장한다.
//엔티티는 데이터베이스와 연동돼서 저장되도록한다.
