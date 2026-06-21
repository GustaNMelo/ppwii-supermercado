-- Script para criar o usuário Admin direto no banco
-- Senha em texto puro: admin123 (troque depois de logar, se quiser usar outra senha
-- gere um novo hash BCrypt e substitua abaixo)

USE supermercado_db;

INSERT INTO users (user_name, user_email, user_passwd)
VALUES ('Administrador', 'admin@supermercado.com', '$2b$10$EDxuOU.hPcm8ZqYeUjajQuBnCEwg7h0Bxru6INTNXi440qGrzaUfu');

INSERT INTO roles (user_id, user_role)
VALUES ((SELECT user_id FROM users WHERE user_email = 'admin@supermercado.com'), 'Admin');
