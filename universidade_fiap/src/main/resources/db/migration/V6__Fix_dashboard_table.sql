-- V6: Correção da tabela dashboard
-- Autor: Sistema de Gestão de Motos
-- Data: 2024

-- Remover a tabela dashboard existente e recriar com a estrutura correta
DROP TABLE IF EXISTS dashboard;

-- Recriar tabela de dashboard com a estrutura correta
CREATE TABLE dashboard (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_motos INT DEFAULT 0,
    motos_disponiveis INT DEFAULT 0,
    motos_alugadas INT DEFAULT 0,
    motos_em_manutencao INT DEFAULT 0,
    total_operacoes INT DEFAULT 0,
    total_check_in INT DEFAULT 0,
    total_check_out INT DEFAULT 0,
    ultima_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    atualizado_por BIGINT,
    FOREIGN KEY (atualizado_por) REFERENCES usuario(id) ON DELETE SET NULL
);

-- Inserir dados iniciais do dashboard
INSERT INTO dashboard (total_motos, motos_disponiveis, motos_alugadas, motos_em_manutencao, total_operacoes, total_check_in, total_check_out, atualizado_por) VALUES
(8, 4, 2, 2, 8, 1, 1, 1);
