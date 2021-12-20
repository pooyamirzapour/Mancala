package com.bol.assignment.repository;

import com.bol.assignment.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for board.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {
}
