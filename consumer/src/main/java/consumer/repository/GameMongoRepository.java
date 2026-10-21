package consumer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import consumer.model.GameDocument;

@Repository
public interface GameMongoRepository extends MongoRepository<GameDocument, Long> {
}