package next.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import next.domain.Board;
import next.domain.BoardRepository;
import next.domain.Deck;
import next.domain.DeckRepository;

@RestController
@RequestMapping("/boards/{boardId}/decks")
public class DeckController {
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private DeckRepository deckRepository;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@PathVariable Long boardId, @RequestBody String name) {
		Board board = boardRepository.findOne(boardId);
		if (board == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		deckRepository.save(new Deck(board, name));
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
