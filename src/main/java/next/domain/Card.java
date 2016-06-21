package next.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(length = 200)
	private String title;

	@Column(length = 5000)
	private String description;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_deck_id"))
	private Deck deck;
	
	Card() {
	}

	public Card(String title, String description) {
		this.title = title;
		this.description = description;
	}
}
