package next.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import core.web.argumentresolver.LoginUser;
import next.domain.board.Board;
import next.domain.board.BoardRepository;
import next.domain.user.User;

@RestController
@RequestMapping("/boards")
public class BoardController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Board> create(@LoginUser User user, @RequestBody Board board) {
		LOGGER.debug("board : {}", board);
		board.createdBy(user);
		Board saved = boardRepository.save(board);
		return new ResponseEntity<Board>(saved, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Board> delete(@PathVariable Long id) {
		Board board = boardRepository.findOne(id);
		if (board == null) {
			return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
		}
		boardRepository.delete(board);
		return new ResponseEntity<Board>(HttpStatus.NO_CONTENT);
	}
}
