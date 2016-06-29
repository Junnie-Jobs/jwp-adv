package next.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Deck {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_board_id"))
	private Board board;
	
	@OneToMany(mappedBy = "deck")
	private List<Card> cards;

	private String name;
	
	public Deck() {
	}
	
	public Deck(Board board, String name) {
		this.board = board;
		this.name = name;
	}
}
