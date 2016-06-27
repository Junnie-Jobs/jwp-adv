package next.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	
	@OneToMany(mappedBy = "board")
	private List<Deck> decks;
	
	public Board() {
	}
	
	public Board(String name) {
		this.name = name;
	}
}
