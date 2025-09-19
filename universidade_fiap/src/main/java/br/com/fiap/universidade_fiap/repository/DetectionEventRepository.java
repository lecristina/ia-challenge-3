package br.com.fiap.universidade_fiap.repository;

import br.com.fiap.universidade_fiap.model.DetectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetectionEventRepository extends JpaRepository<DetectionEvent, Long> {
    
    List<DetectionEvent> findTop10ByOrderByTimestampDesc();
    
    List<DetectionEvent> findByEventTypeOrderByTimestampDesc(String eventType);
    
    List<DetectionEvent> findTop50ByOrderByTimestampDesc();
}
