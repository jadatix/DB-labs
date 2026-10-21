package producer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "sync_state")
public class SyncState {

    @Id
    @Column(name = "sync_key")
    private String syncKey;

    @Column(name = "last_sync_timestamp", nullable = false)
    private LocalDateTime lastSyncTimestamp;

    public SyncState() {}

    public SyncState(String syncKey, LocalDateTime lastSyncTimestamp) {
        this.syncKey = syncKey;
        this.lastSyncTimestamp = lastSyncTimestamp;
    }

    public String getSyncKey() {
        return syncKey;
    }
    public void setSyncKey(String syncKey) {
        this.syncKey = syncKey;
    }
    public LocalDateTime getLastSyncTimestamp() {
        return lastSyncTimestamp;
    }
    public void setLastSyncTimestamp(LocalDateTime lastSyncTimestamp) {
        this.lastSyncTimestamp = lastSyncTimestamp;
    }
}