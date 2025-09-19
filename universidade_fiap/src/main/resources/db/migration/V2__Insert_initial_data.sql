-- V2: Inserção de dados iniciais
-- Autor: Sistema de Gestão de Motos
-- Data: 2024

-- Inserir usuários iniciais
INSERT INTO usuario (nome_filial, email, senha_hash, cnpj, endereco, telefone, perfil, data_criacao) VALUES
('Admin TrackZone', 'admin@teste.com', '$2a$12$zkzF20LzOQfVTcd.nC/8EuS5q3ctGDpg0wX2KRqhtaSQVVwqiidmC', '12345678000199', 'Rua das Flores 123 São Paulo SP', '(11) 99999-9999', 'ADMIN', NOW()),
('Filial Centro', 'gerente@teste.com', '$2a$12$zkzF20LzOQfVTcd.nC/8EuS5q3ctGDpg0wX2KRqhtaSQVVwqiidmC', '98765432000188', 'Av. Paulista 456 São Paulo SP', '(11) 88888-8888', 'GERENTE', NOW()),
('Operador Filial', 'operador@teste.com', '$2a$12$zkzF20LzOQfVTcd.nC/8EuS5q3ctGDpg0wX2KRqhtaSQVVwqiidmC', '11111111000111', 'Rua da Consolação 789 São Paulo SP', '(11) 77777-7777', 'OPERADOR', NOW());

-- Inserir motos iniciais
INSERT INTO moto (placa, chassi, motor, usuario_id, data_criacao) VALUES
('ABC-1234', '1HGBH41JXMN109186', 'Honda CG 160', 1, NOW()),
('DEF-5678', '1HGBH41JXMN109187', 'Yamaha Fazer 250', 1, NOW()),
('GHI-9012', '1HGBH41JXMN109188', 'Honda CB 300', 2, NOW()),
('JKL-3456', '1HGBH41JXMN109189', 'Kawasaki Ninja 300', 2, NOW()),
('MNO-7890', '1HGBH41JXMN109190', 'Suzuki GSX-R 600', 3, NOW()),
('PQR-1234', '1HGBH41JXMN109191', 'Honda PCX 160', 3, NOW()),
('STU-5678', '1HGBH41JXMN109192', 'Yamaha NMAX 160', 1, NOW()),
('VWX-9012', '1HGBH41JXMN109193', 'Honda Biz 125', 2, NOW());

-- Inserir status iniciais das motos (usando os ENUMs corretos)
INSERT INTO status_moto (status, descricao, area, data_status, moto_id, usuario_id) VALUES
('PRONTA', 'Moto disponível para uso', 'Garagem Principal', NOW(), 1, 1),
('ALUGADA', 'Moto em operação de entrega', 'Zona Sul', NOW(), 2, 2),
('MANUTENCAO_AGENDADA', 'Moto em manutenção preventiva', 'Oficina', NOW(), 3, 1),
('PRONTA', 'Moto disponível para uso', 'Garagem Principal', NOW(), 4, 2),
('DANOS_ESTRUTURAIS', 'Moto com problema técnico', 'Oficina', NOW(), 5, 3),
('PRONTA', 'Moto disponível para uso', 'Garagem Principal', NOW(), 6, 3),
('EM_USO', 'Moto em operação de coleta', 'Zona Norte', NOW(), 7, 1),
('AGUARDANDO_ALUGUEL', 'Moto aguardando aluguel', 'Garagem Secundária', NOW(), 8, 2);

-- Inserir operações iniciais (usando os ENUMs corretos)
INSERT INTO operacao (tipo_operacao, descricao, data_operacao, moto_id, usuario_id) VALUES
('ENTREGA', 'Entrega de pedido na região central', NOW(), 1, 2),
('COLETA', 'Coleta de encomenda no shopping', NOW(), 2, 3),
('MANUTENCAO', 'Manutenção preventiva agendada', NOW(), 3, 1),
('TRANSFERENCIA', 'Transferência entre filiais', NOW(), 4, 2),
('ENTREGA', 'Entrega expressa urgente', NOW(), 5, 3),
('COLETA', 'Coleta de retorno', NOW(), 6, 2),
('CHECK_IN', 'Check-in de moto retornada', NOW(), 7, 1),
('CHECK_OUT', 'Check-out de moto alugada', NOW(), 8, 2);
