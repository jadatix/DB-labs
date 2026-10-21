package producer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import producer.entity.Game;
import producer.entity.SyncState;
import producer.repository.GameRepository;
import producer.repository.SyncStateRepository;

@Service
public class GameProducerService {
	
	private static final String SYNC_KEY = "GAMES_SYNC";
	private static final String TOPIC = "games";
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private SyncStateRepository syncRepository;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
    private ObjectMapper objectMapper;
    
    private final Logger logger = LoggerFactory.getLogger(GameProducerService.class);
    
    private LocalDateTime defaultSyncTimestamp = LocalDateTime.of(1970, 1, 1, 0, 0);
    
    @Scheduled(fixedRateString = "${sync.interval.ms:5000}")
    public void syncModifiedGames() {
    	SyncState syncState = syncRepository.findById(SYNC_KEY).orElseGet(() -> new SyncState(SYNC_KEY, defaultSyncTimestamp));
    	
    	LocalDateTime lastSyncTimestamp = syncState.getLastSyncTimestamp();
        List<Game> modifiedGames = gameRepository.findByUpdatedAtAfter(lastSyncTimestamp);

        if (modifiedGames.isEmpty()) {
            return;
        }

        logger.info("Found " + modifiedGames.size() + " new/updated game(s) since " + lastSyncTimestamp);

        LocalDateTime maxUpdatedAt = lastSyncTimestamp;

        for (Game game : modifiedGames) {
            try {
                String jsonPayload = objectMapper.writeValueAsString(game);

                kafkaTemplate.send(TOPIC, game.getId().toString(), jsonPayload);
                logger.info("----Published to Kafka [Key: " + game.getId() + "]: " + jsonPayload);

                if (game.getUpdatedAt().isAfter(maxUpdatedAt)) {
                    maxUpdatedAt = game.getUpdatedAt();
                }
            } catch (JsonProcessingException e) {
                logger.error("-----Failed to serialize Game ID " + game.getId() + ": " + e.getMessage());
            }
        }

        syncState.setLastSyncTimestamp(maxUpdatedAt);
        syncRepository.save(syncState);
    }
}
