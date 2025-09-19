-- Script para limpar o banco de dados e permitir que o Flyway execute as migrações
-- Execute este script no seu banco de dados MySQL

-- Desabilitar verificação de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 0;

-- Remover todas as tabelas
DROP TABLE IF EXISTS operacoes;
DROP TABLE IF EXISTS status_motos;
DROP TABLE IF EXISTS motos;
DROP TABLE IF EXISTS usuarios;

-- Remover tabela de controle do Flyway
DROP TABLE IF EXISTS flyway_schema_history;

-- Reabilitar verificação de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 1;

-- Confirmar limpeza
SELECT 'Banco de dados limpo com sucesso!' as resultado;
