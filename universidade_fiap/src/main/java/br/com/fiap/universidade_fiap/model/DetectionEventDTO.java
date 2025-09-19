package br.com.fiap.universidade_fiap.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DetectionEventDTO {
    private String eventType;
    private int x;
    private int y;
    private LocalDateTime timestamp;
}
