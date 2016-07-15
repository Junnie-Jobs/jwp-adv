package next.domain.board;

import java.util.List;

import javax.persistence.Column;
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
import next.domain.user.User;

@Entity(name = "board")
@Data
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(
			foreignKey = @ForeignKey(name = "fk_creator_id"), 
			nullable = false)
	private User creator;
	
	@JsonIgnore	
	@OneToMany(mappedBy = "board")
	private List<Deck> decks;
	
	public Board() {
	}
	
	public Board(User user, String name) {
		this.creator = user;
		this.name = name;
	}
	
	public void createdBy(User user) {
		this.creator = user;
	}
}
