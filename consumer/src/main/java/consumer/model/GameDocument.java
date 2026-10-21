package consumer.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class GameDocument {

	@Id
	private Long id;
	private String name;
	private LocalDateTime updatedAt;
    private LocalDate releaseDate;
    private String currentGameVersion;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getCurrentGameVersion() {
		return currentGameVersion;
	}
	public void setCurrentGameVersion(String currentGameVersion) {
		this.currentGameVersion = currentGameVersion;
	}
    
    
}
