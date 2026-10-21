package consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import consumer.model.GameDocument;
import consumer.repository.GameMongoRepository;

@Service
public class GameConsumerService {

	@Autowired
    private GameMongoRepository repository;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private final Logger logger = LoggerFactory.getLogger(GameMongoRepository.class);
    
    @KafkaListener(topics = "games", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String jsonPayload) {
        try {
            GameDocument game = objectMapper.readValue(jsonPayload, GameDocument.class);

            repository.save(game);

            logger.info("-----Upserted to MongoDB [ID: " + game.getId() + "]: " + game.getName());

        } catch (JsonProcessingException e) {
            logger.error("-----Failed to deserialize Kafka message: " + e.getMessage());
        }
    }
}