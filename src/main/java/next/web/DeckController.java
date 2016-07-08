package next.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import next.domain.board.Board;
import next.domain.board.BoardRepository;
import next.domain.board.Deck;
import next.domain.board.DeckRepository;

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
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Deck> show(@PathVariable Long id) {
		Deck deck = deckRepository.findOne(id);
		if (deck == null) {
			return new ResponseEntity<Deck>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Deck>(deck, HttpStatus.OK);
	}
}
