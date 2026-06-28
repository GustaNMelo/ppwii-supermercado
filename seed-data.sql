USE supermercado_db;

-- ─── CATEGORIAS ───────────────────────────────────────────────
INSERT INTO categorias (categoria_nome, categoria_descricao) VALUES
('Hortifruti',  'Frutas, legumes e verduras frescos'),
('Laticínios',  'Leite, queijo, iogurte e derivados'),
('Carnes',      'Carnes bovinas, suínas e aves'),
('Bebidas',     'Sucos, refrigerantes, águas e cervejas'),
('Limpeza',     'Produtos de limpeza doméstica'),
('Padaria',     'Pães, bolos e confeitaria'),
('Mercearia',   'Arroz, feijão, macarrão e enlatados');

-- ─── FORNECEDORES ─────────────────────────────────────────────
INSERT INTO fornecedores (fornecedor_nome, fornecedor_cnpj, fornecedor_email, fornecedor_telefone) VALUES
('Fazenda Boa Terra',       '12.345.678/0001-01', 'contato@boaterra.com.br',      '(34) 99801-1100'),
('Laticínios Vale Verde',   '23.456.789/0001-02', 'vendas@valeverde.com.br',      '(34) 99802-2200'),
('Frigorífico Central',     '34.567.890/0001-03', 'pedidos@frigocentral.com.br',  '(34) 99803-3300'),
('Distribuidora BebMax',    '45.678.901/0001-04', 'comercial@bebmax.com.br',      '(34) 99804-4400'),
('Produtos Limpar Bem',     '56.789.012/0001-05', 'vendas@limparbem.com.br',      '(34) 99805-5500'),
('Padaria Grão de Ouro',    '67.890.123/0001-06', 'contato@graodeouro.com.br',    '(34) 99806-6600'),
('Atacado Safra',           '78.901.234/0001-07', 'atendimento@safra.com.br',     '(34) 99807-7700');

-- ─── PRODUTOS ─────────────────────────────────────────────────
INSERT INTO produtos (produto_nome, produto_preco, produto_descricao, categoria_id) VALUES
-- Hortifruti
('Banana Prata (kg)',        2.99,  'Banana prata fresca, unidade por kg',                (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Hortifruti')),
('Maçã Fuji (kg)',           7.49,  'Maçã fuji importada, unidade por kg',                (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Hortifruti')),
('Tomate Salada (kg)',       4.29,  'Tomate salada firme, ideal para saladas',             (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Hortifruti')),
('Alface Crespa (un)',       1.99,  'Alface crespa fresca, unidade',                      (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Hortifruti')),
-- Laticínios
('Leite Integral 1L',        4.89,  'Leite integral longa vida 1 litro',                  (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Laticínios')),
('Queijo Mussarela (kg)',   39.90,  'Queijo mussarela fatiado, unidade por kg',           (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Laticínios')),
('Iogurte Natural 170g',     2.49,  'Iogurte natural sem sabor 170g',                     (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Laticínios')),
('Manteiga com Sal 200g',    8.99,  'Manteiga com sal tablete 200g',                      (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Laticínios')),
-- Carnes
('Frango Inteiro (kg)',      11.99, 'Frango inteiro resfriado, unidade por kg',            (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Carnes')),
('Picanha Bovina (kg)',      69.90, 'Picanha bovina premium, unidade por kg',              (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Carnes')),
('Linguiça Toscana (kg)',    18.90, 'Linguiça toscana suína, unidade por kg',              (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Carnes')),
-- Bebidas
('Água Mineral 500ml',       1.49,  'Água mineral sem gás 500ml',                         (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Bebidas')),
('Refrigerante Cola 2L',     7.99,  'Refrigerante sabor cola garrafa 2 litros',           (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Bebidas')),
('Suco de Laranja 1L',       6.49,  'Suco de laranja integral 1 litro',                   (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Bebidas')),
-- Limpeza
('Detergente 500ml',         2.29,  'Detergente líquido neutro 500ml',                    (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Limpeza')),
('Sabão em Pó 1kg',          9.99,  'Sabão em pó multiação 1kg',                          (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Limpeza')),
('Água Sanitária 1L',        3.49,  'Água sanitária alvejante 1 litro',                   (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Limpeza')),
-- Padaria
('Pão Francês (un)',         0.59,  'Pão francês crocante, unidade',                      (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Padaria')),
('Pão de Forma 500g',        5.99,  'Pão de forma tradicional 500g',                      (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Padaria')),
-- Mercearia
('Arroz Branco 5kg',        24.90,  'Arroz branco tipo 1, pacote 5kg',                    (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Mercearia')),
('Feijão Carioca 1kg',       8.49,  'Feijão carioca tipo 1, pacote 1kg',                  (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Mercearia')),
('Macarrão Espaguete 500g',  3.29,  'Macarrão espaguete grano duro 500g',                 (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Mercearia')),
('Azeite Extra Virgem 500ml',24.99, 'Azeite de oliva extra virgem 500ml',                 (SELECT categoria_id FROM categorias WHERE categoria_nome = 'Mercearia'));

-- ─── PRODUTO × FORNECEDOR ─────────────────────────────────────
INSERT INTO produto_fornecedor (produto_id, fornecedor_id) VALUES
((SELECT produto_id FROM produtos WHERE produto_nome = 'Banana Prata (kg)'),       (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Fazenda Boa Terra')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Maçã Fuji (kg)'),          (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Fazenda Boa Terra')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Tomate Salada (kg)'),      (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Fazenda Boa Terra')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Alface Crespa (un)'),      (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Fazenda Boa Terra')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Leite Integral 1L'),       (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Laticínios Vale Verde')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Queijo Mussarela (kg)'),   (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Laticínios Vale Verde')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Iogurte Natural 170g'),    (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Laticínios Vale Verde')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Manteiga com Sal 200g'),   (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Laticínios Vale Verde')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Frango Inteiro (kg)'),     (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Frigorífico Central')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Picanha Bovina (kg)'),     (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Frigorífico Central')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Linguiça Toscana (kg)'),   (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Frigorífico Central')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Água Mineral 500ml'),      (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Distribuidora BebMax')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Refrigerante Cola 2L'),    (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Distribuidora BebMax')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Suco de Laranja 1L'),      (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Distribuidora BebMax')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Detergente 500ml'),        (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Produtos Limpar Bem')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Sabão em Pó 1kg'),         (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Produtos Limpar Bem')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Água Sanitária 1L'),       (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Produtos Limpar Bem')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Pão Francês (un)'),        (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Padaria Grão de Ouro')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Pão de Forma 500g'),       (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Padaria Grão de Ouro')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Arroz Branco 5kg'),        (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Atacado Safra')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Feijão Carioca 1kg'),      (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Atacado Safra')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Macarrão Espaguete 500g'), (SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Atacado Safra')),
((SELECT produto_id FROM produtos WHERE produto_nome = 'Azeite Extra Virgem 500ml'),(SELECT fornecedor_id FROM fornecedores WHERE fornecedor_nome = 'Atacado Safra'));

-- ─── CLIENTES ─────────────────────────────────────────────────
INSERT INTO clientes (cliente_nome, cliente_cpf, cliente_email, cliente_telefone) VALUES
('Ana Beatriz Souza',   '123.456.789-01', 'ana.souza@email.com',     '(34) 99911-1111'),
('Carlos Eduardo Lima', '234.567.890-02', 'carlos.lima@email.com',   '(34) 99922-2222'),
('Fernanda Oliveira',   '345.678.901-03', 'fernanda.oli@email.com',  '(34) 99933-3333'),
('João Pedro Alves',    '456.789.012-04', 'joao.alves@email.com',    '(34) 99944-4444'),
('Mariana Costa',       '567.890.123-05', 'mariana.costa@email.com', '(34) 99955-5555'),
('Ricardo Santos',      '678.901.234-06', 'ricardo.san@email.com',   '(34) 99966-6666'),
('Patrícia Mendes',     '789.012.345-07', 'patricia.men@email.com',  '(34) 99977-7777');
