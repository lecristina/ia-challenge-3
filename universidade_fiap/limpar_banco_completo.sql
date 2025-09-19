-- Script para limpar completamente o banco de dados
-- Execute este script antes de reiniciar a aplicação

-- Desabilitar verificação de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 0;

-- Limpar todas as tabelas
TRUNCATE TABLE auditoria;
TRUNCATE TABLE status_moto;
TRUNCATE TABLE operacao;
TRUNCATE TABLE moto;
TRUNCATE TABLE usuario;
TRUNCATE TABLE dashboard;
TRUNCATE TABLE configuracao;
TRUNCATE TABLE log_sistema;

-- Reabilitar verificação de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 1;

-- Resetar auto_increment
ALTER TABLE usuario AUTO_INCREMENT = 1;
ALTER TABLE moto AUTO_INCREMENT = 1;
ALTER TABLE status_moto AUTO_INCREMENT = 1;
ALTER TABLE operacao AUTO_INCREMENT = 1;
ALTER TABLE auditoria AUTO_INCREMENT = 1;
ALTER TABLE dashboard AUTO_INCREMENT = 1;
ALTER TABLE configuracao AUTO_INCREMENT = 1;
ALTER TABLE log_sistema AUTO_INCREMENT = 1;

-- Verificar se as tabelas estão vazias
SELECT 'usuario' as tabela, COUNT(*) as registros FROM usuario
UNION ALL
SELECT 'moto' as tabela, COUNT(*) as registros FROM moto
UNION ALL
SELECT 'status_moto' as tabela, COUNT(*) as registros FROM status_moto
UNION ALL
SELECT 'operacao' as tabela, COUNT(*) as registros FROM operacao
UNION ALL
SELECT 'dashboard' as tabela, COUNT(*) as registros FROM dashboard;