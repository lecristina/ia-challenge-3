-- V3: Criação de tabelas para dashboard e relatórios
-- Autor: Sistema de Gestão de Motos
-- Data: 2024

-- Tabela de dashboard (cache de métricas)
CREATE TABLE IF NOT EXISTS dashboard (
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

-- Tabela de configurações do sistema
CREATE TABLE IF NOT EXISTS configuracao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chave VARCHAR(100) NOT NULL UNIQUE,
    valor TEXT,
    descricao VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de logs do sistema
CREATE TABLE IF NOT EXISTS log_sistema (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nivel ENUM('INFO', 'WARN', 'ERROR', 'DEBUG') NOT NULL,
    mensagem TEXT NOT NULL,
    classe_origem VARCHAR(255),
    usuario_id BIGINT,
    data_log TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

-- Inserir configurações iniciais
INSERT INTO configuracao (chave, valor, descricao) VALUES
('sistema.nome', 'Gestão de Motos Mottu', 'Nome do sistema'),
('sistema.versao', '1.0.0', 'Versão atual do sistema'),
('manutencao.intervalo_dias', '30', 'Intervalo em dias para manutenção preventiva'),
('operacao.tempo_maximo_horas', '8', 'Tempo máximo de operação em horas'),
('notificacao.email_ativo', 'true', 'Se notificações por email estão ativas'),
('backup.frequencia_dias', '7', 'Frequência de backup em dias');

-- Inserir dados iniciais do dashboard
INSERT INTO dashboard (total_motos, motos_disponiveis, motos_alugadas, motos_em_manutencao, total_operacoes, total_check_in, total_check_out, atualizado_por) VALUES
(8, 4, 2, 2, 8, 1, 1, 1);

-- Índices para performance
CREATE INDEX idx_log_sistema_data ON log_sistema(data_log);
CREATE INDEX idx_log_sistema_nivel ON log_sistema(nivel);
CREATE INDEX idx_configuracao_chave ON configuracao(chave);
