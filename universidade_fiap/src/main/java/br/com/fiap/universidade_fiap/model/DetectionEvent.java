package br.com.fiap.universidade_fiap.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "detection_event")
@Data
public class DetectionEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "event_type", nullable = false)
    private String eventType;
    
    @Column(nullable = false)
    private int x;
    
    @Column(nullable = false)
    private int y;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
