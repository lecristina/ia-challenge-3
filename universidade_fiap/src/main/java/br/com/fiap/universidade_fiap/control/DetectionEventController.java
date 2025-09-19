package br.com.fiap.universidade_fiap.control;

import br.com.fiap.universidade_fiap.model.DetectionEvent;
import br.com.fiap.universidade_fiap.model.DetectionEventDTO;
import br.com.fiap.universidade_fiap.repository.DetectionEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detections")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DetectionEventController {
    
    private final DetectionEventRepository repository;
    
    @PostMapping
    public ResponseEntity<DetectionEvent> saveEvent(@RequestBody Map<String, Object> data) {
        try {
            DetectionEvent event = new DetectionEvent();
            
            // Campos obrigatórios
            event.setEventType((String) data.getOrDefault("eventType", "moto_detectada"));
            event.setX(((Number) data.getOrDefault("x", 0)).intValue());
            event.setY(((Number) data.getOrDefault("y", 0)).intValue());
            
            // Timestamp
            if (data.containsKey("timestamp")) {
                String timestampStr = (String) data.get("timestamp");
                if (timestampStr != null && !timestampStr.isEmpty()) {
                    event.setTimestamp(LocalDateTime.parse(timestampStr));
                } else {
                    event.setTimestamp(LocalDateTime.now());
                }
            } else {
                event.setTimestamp(LocalDateTime.now());
            }
            
            DetectionEvent savedEvent = repository.save(event);
            return ResponseEntity.ok(savedEvent);
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar evento: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/recent")
    public List<DetectionEvent> getRecentEvents() {
        try {
            return repository.findTop10ByOrderByTimestampDesc();
        } catch (Exception e) {
            System.err.println("Erro ao buscar eventos recentes: " + e.getMessage());
            return List.of();
        }
    }
    
    @GetMapping("/recent/50")
    public List<DetectionEvent> getRecent50Events() {
        try {
            return repository.findTop50ByOrderByTimestampDesc();
        } catch (Exception e) {
            System.err.println("Erro ao buscar 50 eventos recentes: " + e.getMessage());
            return List.of();
        }
    }
    
    @GetMapping("/by-type/{eventType}")
    public List<DetectionEvent> getEventsByType(@PathVariable String eventType) {
        try {
            return repository.findByEventTypeOrderByTimestampDesc(eventType);
        } catch (Exception e) {
            System.err.println("Erro ao buscar eventos por tipo: " + e.getMessage());
            return List.of();
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getEventCount() {
        try {
            return ResponseEntity.ok(repository.count());
        } catch (Exception e) {
            System.err.println("Erro ao contar eventos: " + e.getMessage());
            return ResponseEntity.ok(0L);
        }
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("total", repository.count());
            stats.put("recent", repository.findTop10ByOrderByTimestampDesc().size());
            stats.put("timestamp", LocalDateTime.now().toString());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("Erro ao buscar estatísticas: " + e.getMessage());
            Map<String, Object> errorStats = new HashMap<>();
            errorStats.put("total", 0L);
            errorStats.put("recent", 0);
            errorStats.put("timestamp", LocalDateTime.now().toString());
            return ResponseEntity.ok(errorStats);
        }
    }
}
