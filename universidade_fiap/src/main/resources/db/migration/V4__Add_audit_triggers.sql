-- V4: Criação de triggers para auditoria automática
-- Autor: Sistema de Gestão de Motos
-- Data: 2024

-- Trigger para auditoria na tabela usuario
DELIMITER $$

CREATE TRIGGER tr_usuario_audit_insert
AFTER INSERT ON usuario
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (tabela_afetada, operacao, registro_id, dados_novos, data_auditoria)
    VALUES ('usuario', 'INSERT', NEW.id, JSON_OBJECT(
        'id', NEW.id,
        'nome_filial', NEW.nome_filial,
        'email', NEW.email,
        'cnpj', NEW.cnpj,
        'perfil', NEW.perfil,
        'data_criacao', NEW.data_criacao
    ), NOW());
END$$

CREATE TRIGGER tr_usuario_audit_update
AFTER UPDATE ON usuario
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (tabela_afetada, operacao, registro_id, dados_anteriores, dados_novos, data_auditoria)
    VALUES ('usuario', 'UPDATE', NEW.id, JSON_OBJECT(
        'id', OLD.id,
        'nome_filial', OLD.nome_filial,
        'email', OLD.email,
        'cnpj', OLD.cnpj,
        'perfil', OLD.perfil,
        'data_criacao', OLD.data_criacao
    ), JSON_OBJECT(
        'id', NEW.id,
        'nome_filial', NEW.nome_filial,
        'email', NEW.email,
        'cnpj', NEW.cnpj,
        'perfil', NEW.perfil,
        'data_criacao', NEW.data_criacao
    ), NOW());
END$$

CREATE TRIGGER tr_usuario_audit_delete
AFTER DELETE ON usuario
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (tabela_afetada, operacao, registro_id, dados_anteriores, data_auditoria)
    VALUES ('usuario', 'DELETE', OLD.id, JSON_OBJECT(
        'id', OLD.id,
        'nome_filial', OLD.nome_filial,
        'email', OLD.email,
        'cnpj', OLD.cnpj,
        'perfil', OLD.perfil,
        'data_criacao', OLD.data_criacao
    ), NOW());
END$$

-- Trigger para auditoria na tabela moto
CREATE TRIGGER tr_moto_audit_insert
AFTER INSERT ON moto
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (tabela_afetada, operacao, registro_id, dados_novos, data_auditoria)
    VALUES ('moto', 'INSERT', NEW.id, JSON_OBJECT(
        'id', NEW.id,
        'placa', NEW.placa,
        'chassi', NEW.chassi,
        'motor', NEW.motor,
        'usuario_id', NEW.usuario_id,
        'data_criacao', NEW.data_criacao
    ), NOW());
END$$

CREATE TRIGGER tr_moto_audit_update
AFTER UPDATE ON moto
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (tabela_afetada, operacao, registro_id, dados_anteriores, dados_novos, data_auditoria)
    VALUES ('moto', 'UPDATE', NEW.id, JSON_OBJECT(
        'id', OLD.id,
        'placa', OLD.placa,
        'chassi', OLD.chassi,
        'motor', OLD.motor,
        'usuario_id', OLD.usuario_id,
        'data_criacao', OLD.data_criacao
    ), JSON_OBJECT(
        'id', NEW.id,
        'placa', NEW.placa,
        'chassi', NEW.chassi,
        'motor', NEW.motor,
        'usuario_id', NEW.usuario_id,
        'data_criacao', NEW.data_criacao
    ), NOW());
END$$

CREATE TRIGGER tr_moto_audit_delete
AFTER DELETE ON moto
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (tabela_afetada, operacao, registro_id, dados_anteriores, data_auditoria)
    VALUES ('moto', 'DELETE', OLD.id, JSON_OBJECT(
        'id', OLD.id,
        'placa', OLD.placa,
        'chassi', OLD.chassi,
        'motor', OLD.motor,
        'usuario_id', OLD.usuario_id,
        'data_criacao', OLD.data_criacao
    ), NOW());
END$$

-- Trigger para auditoria na tabela operacao
CREATE TRIGGER tr_operacao_audit_insert
AFTER INSERT ON operacao
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (tabela_afetada, operacao, registro_id, dados_novos, data_auditoria)
    VALUES ('operacao', 'INSERT', NEW.id, JSON_OBJECT(
        'id', NEW.id,
        'tipo_operacao', NEW.tipo_operacao,
        'descricao', NEW.descricao,
        'moto_id', NEW.moto_id,
        'usuario_id', NEW.usuario_id,
        'data_operacao', NEW.data_operacao
    ), NOW());
END$$

DELIMITER ;

-- Inserir log inicial do sistema
INSERT INTO log_sistema (nivel, mensagem, classe_origem) VALUES
('INFO', 'Sistema iniciado com sucesso', 'UniversidadeFiapApplication'),
('INFO', 'Migrações do Flyway executadas', 'FlywayMigration'),
('INFO', 'Triggers de auditoria criados', 'DatabaseSetup');
