-- Script para remover completamente a tabela de auditoria
-- Execute este script no seu banco de dados MySQL

-- Remover a tabela auditoria se ela existir
DROP TABLE IF EXISTS auditoria;

-- Confirmar remoção
SELECT 'Tabela auditoria removida com sucesso!' as resultado;
