package next.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String title;

	private String description;
	
	Card() {
	}

	public Card(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", title=" + title + ", description=" + description + "]";
	}
}
