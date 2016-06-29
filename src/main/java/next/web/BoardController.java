package next.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import next.domain.Board;
import next.domain.BoardRepository;

@RestController
@RequestMapping("/boards")
public class BoardController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Board> create(@RequestBody Board board, UriComponentsBuilder ucBuilder) {
		LOGGER.debug("board : {}", board);
		
		Board saved = boardRepository.save(board);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(ucBuilder.path("/boards/{id}").buildAndExpand(saved.getId()).toUri());
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
