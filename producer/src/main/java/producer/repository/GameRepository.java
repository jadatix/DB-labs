package producer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import producer.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
	
	List<Game> findByUpdatedAtAfter(LocalDateTime lastSyncTime);
}
