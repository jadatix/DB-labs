package producer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import producer.entity.SyncState;

@Repository
public interface SyncStateRepository extends JpaRepository<SyncState, String> {

}
