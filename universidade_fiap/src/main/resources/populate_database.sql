-- Script adicional para popular o banco de dados
-- Execute este script após o data.sql

-- Adicionar mais usuários
INSERT INTO usuarios (nome_filial, email, senha_hash, cnpj, endereco, telefone, perfil, data_criacao) VALUES
('Filial Norte', 'norte@teste.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '22222222000222', 'Av. Tietê, 1000 - São Paulo/SP', '(11) 66666-6666', 'GERENTE', NOW()),
('Filial Sul', 'sul@teste.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '33333333000333', 'Rua Augusta, 2000 - São Paulo/SP', '(11) 55555-5555', 'OPERADOR', NOW()),
('Filial Leste', 'leste@teste.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', '44444444000444', 'Av. Faria Lima, 3000 - São Paulo/SP', '(11) 44444-4444', 'OPERADOR', NOW());

-- Adicionar mais motos
INSERT INTO motos (placa, chassi, motor, usuario_id, data_criacao) VALUES
('XYZ-9999', 'CHASSI011', 'Honda CB 1000', 1, NOW()),
('WUV-8888', 'CHASSI012', 'Yamaha R1', 1, NOW()),
('TSR-7777', 'CHASSI013', 'Kawasaki ZX-10R', 1, NOW()),
('QPO-6666', 'CHASSI014', 'Suzuki GSX-R 1000', 1, NOW()),
('NML-5555', 'CHASSI015', 'Honda CBR 1000', 1, NOW()),
('KJI-4444', 'CHASSI016', 'Yamaha MT-09', 1, NOW()),
('HGF-3333', 'CHASSI017', 'Kawasaki Ninja 650', 1, NOW()),
('EDC-2222', 'CHASSI018', 'Suzuki SV 650', 1, NOW()),
('BAZ-1111', 'CHASSI019', 'Honda Hornet 600', 1, NOW()),
('YXC-0000', 'CHASSI020', 'Yamaha XJ6', 1, NOW());

-- Adicionar status para as novas motos
INSERT INTO status_motos (moto_id, status, area, usuario_id, data_criacao) VALUES
(11, 'PRONTA', 'Garagem Principal', 1, NOW()),
(12, 'ALUGADA', 'Em Uso', 1, NOW()),
(13, 'MANUTENCAO_AGENDADA', 'Oficina', 1, NOW()),
(14, 'REPARO_SIMPLES', 'Oficina', 1, NOW()),
(15, 'DANOS_ESTRUTURAIS', 'Oficina', 1, NOW()),
(16, 'MOTOR_DEFEITUOSO', 'Oficina', 1, NOW()),
(17, 'AGUARDANDO_ALUGUEL', 'Garagem Secundária', 1, NOW()),
(18, 'SEM_PLACA', 'Garagem Principal', 1, NOW()),
(19, 'PENDENTE', 'Garagem Principal', 1, NOW()),
(20, 'PRONTA', 'Garagem Secundária', 1, NOW());

-- Atualizar dashboard com novos dados
UPDATE dashboard SET 
    total_motos = 20,
    motos_alugadas = 4,
    motos_disponiveis = 6,
    motos_em_manutencao = 10,
    total_operacoes = 20,
    total_check_in = 16,
    total_check_out = 4,
    ultima_atualizacao = NOW()
WHERE id = 1;
