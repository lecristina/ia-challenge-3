-- V8: Criação da tabela de eventos de detecção
-- Autor: Sistema de Detecção de Motos
-- Data: 2024

CREATE TABLE detection_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_type VARCHAR(255) NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice para melhor performance nas consultas por timestamp
CREATE INDEX idx_detection_event_timestamp ON detection_event(timestamp DESC);

-- Índice para consultas por tipo de evento
CREATE INDEX idx_detection_event_type ON detection_event(event_type);
