-- V1: Criação das tabelas principais
-- Autor: Sistema de Gestão de Motos
-- Data: 2024

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_filial VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    endereco VARCHAR(500),
    telefone VARCHAR(20),
    perfil ENUM('ADMIN', 'GERENTE', 'OPERADOR') NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de motos
CREATE TABLE IF NOT EXISTS moto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    chassi VARCHAR(17) NOT NULL UNIQUE,
    motor VARCHAR(100),
    usuario_id BIGINT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela de status das motos
CREATE TABLE IF NOT EXISTS status_moto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status ENUM('DISPONIVEL', 'EM_USO', 'MANUTENCAO', 'INDISPONIVEL', 'PENDENTE', 'REPARO_SIMPLES', 'DANOS_ESTRUTURAIS', 'MOTOR_DEFEITUOSO', 'MANUTENCAO_AGENDADA', 'PRONTA', 'SEM_PLACA', 'ALUGADA', 'AGUARDANDO_ALUGUEL') NOT NULL,
    descricao TEXT,
    area VARCHAR(50) NOT NULL,
    data_status TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    moto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (moto_id) REFERENCES moto(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela de operações
CREATE TABLE IF NOT EXISTS operacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_operacao ENUM('ENTREGA', 'COLETA', 'MANUTENCAO', 'TRANSFERENCIA', 'CHECK_IN', 'CHECK_OUT') NOT NULL,
    descricao TEXT,
    data_operacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    moto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (moto_id) REFERENCES moto(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela de auditoria
CREATE TABLE IF NOT EXISTS auditoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tabela_afetada VARCHAR(100) NOT NULL,
    operacao ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    registro_id BIGINT NOT NULL,
    dados_anteriores JSON,
    dados_novos JSON,
    usuario_id BIGINT,
    data_auditoria TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

-- Índices para performance
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_usuario_cnpj ON usuario(cnpj);
CREATE INDEX idx_moto_placa ON moto(placa);
CREATE INDEX idx_moto_chassi ON moto(chassi);
CREATE INDEX idx_operacao_data ON operacao(data_operacao);
CREATE INDEX idx_auditoria_data ON auditoria(data_auditoria);
