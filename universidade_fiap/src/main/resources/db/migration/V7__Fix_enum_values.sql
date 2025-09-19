-- V7: Correção dos valores ENUM
-- Autor: Sistema de Gestão de Motos
-- Data: 2024

-- Alterar ENUM da tabela status_moto para incluir todos os valores
ALTER TABLE status_moto 
MODIFY COLUMN status ENUM(
    'DISPONIVEL', 
    'EM_USO', 
    'MANUTENCAO', 
    'INDISPONIVEL', 
    'PENDENTE', 
    'REPARO_SIMPLES', 
    'DANOS_ESTRUTURAIS', 
    'MOTOR_DEFEITUOSO', 
    'MANUTENCAO_AGENDADA', 
    'PRONTA', 
    'SEM_PLACA', 
    'ALUGADA', 
    'AGUARDANDO_ALUGUEL'
) NOT NULL;

-- Alterar ENUM da tabela operacao para incluir CHECK_IN e CHECK_OUT
ALTER TABLE operacao 
MODIFY COLUMN tipo_operacao ENUM(
    'ENTREGA', 
    'COLETA', 
    'MANUTENCAO', 
    'TRANSFERENCIA', 
    'CHECK_IN', 
    'CHECK_OUT'
) NOT NULL;
