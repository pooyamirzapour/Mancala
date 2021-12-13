package com.bol.assignment.repository;

import com.bol.assignment.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for board.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
