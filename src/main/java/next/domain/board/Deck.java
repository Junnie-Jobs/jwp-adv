package next.domain.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Deck {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(
			foreignKey = @ForeignKey(name = "fk_board_id"), 
			nullable = false)
	private Board board;
	
	@JsonIgnore
	@OneToMany(mappedBy = "deck")
	private List<Card> cards = new ArrayList<>();

	private String name;
	
	public Deck() {
	}
	
	public Deck(Board board, String name) {
		this.board = board;
		this.name = name;
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
}
