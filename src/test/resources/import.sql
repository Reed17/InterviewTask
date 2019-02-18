INSERT INTO app_user (user_id, username, email, password, wallets) VALUES (1, 'Egor', 'Egor@gmail.com', '$2a$10$Tc7eieQCxKhxYpCQRqDptebCjSw6qUTg3lvb1tcCo8bBEI7Alk5oi', 1);
INSERT INTO app_user (user_id, username, email, password, wallets) VALUES (2, 'Ivan', 'Ivan@gmail.com', '$2a$10$Tc7eieQCxKhxYpCQRqDptebCjSw6qUTg3lvb1tcCo8bBEI7Alk5oi', 2);
INSERT INTO app_user (user_id, username, email, password, wallets) VALUES (3, 'Gena', 'Gena@gmail.com', '$2a$10$Tc7eieQCxKhxYpCQRqDptebCjSw6qUTg3lvb1tcCo8bBEI7Alk5oi', 3);
INSERT INTO app_user (user_id, username, email, password, wallets) VALUES (4, 'George', 'George@gmail.com', '$2a$10$Tc7eieQCxKhxYpCQRqDptebCjSw6qUTg3lvb1tcCo8bBEI7Alk5oi', 4);
INSERT INTO app_user (user_id, username, email, password, wallets) VALUES (5, 'Inna', 'Inna@gmail.com', '$2a$10$Tc7eieQCxKhxYpCQRqDptebCjSw6qUTg3lvb1tcCo8bBEI7Alk5oi', 5);
INSERT INTO app_user (user_id, username, email, password, wallets) VALUES (6, 'Elena', 'Elena@gmail.com', '$2a$10$Tc7eieQCxKhxYpCQRqDptebCjSw6qUTg3lvb1tcCo8bBEI7Alk5oi', 6);
INSERT INTO app_user (user_id, username, email, password, wallets) VALUES (7, 'Julie', 'Julie@gmail.com', '$2a$10$Tc7eieQCxKhxYpCQRqDptebCjSw6qUTg3lvb1tcCo8bBEI7Alk5oi', 7);

INSERT INTO app_user_roles (user_id, roles) VALUES (1, 'USER');
INSERT INTO app_user_roles (user_id, roles) VALUES (2, 'USER');
INSERT INTO app_user_roles (user_id, roles) VALUES (3, 'USER');
INSERT INTO app_user_roles (user_id, roles) VALUES (4, 'USER');
INSERT INTO app_user_roles (user_id, roles) VALUES (5, 'USER');
INSERT INTO app_user_roles (user_id, roles) VALUES (6, 'USER');
INSERT INTO app_user_roles (user_id, roles) VALUES (7, 'USER');

INSERT INTO wallet (wallet_id, currency, balance) VALUES (1, 'UAH', 250000);
INSERT INTO wallet (wallet_id, currency, balance) VALUES (2, 'UAH', 10000);
INSERT INTO wallet (wallet_id, currency, balance) VALUES (3, 'USD', 7054);
INSERT INTO wallet (wallet_id, currency, balance) VALUES (4, 'USD', 5005);
INSERT INTO wallet (wallet_id, currency, balance) VALUES (5, 'EUR', 1025);
INSERT INTO wallet (wallet_id, currency, balance) VALUES (6, 'EUR', 908);
INSERT INTO wallet (wallet_id, currency, balance) VALUES (7, 'EUR', 100);

INSERT INTO app_user_wallets (user_user_id, wallets_wallet_id) VALUES (1, 1);
INSERT INTO app_user_wallets (user_user_id, wallets_wallet_id) VALUES (2, 2);
INSERT INTO app_user_wallets (user_user_id, wallets_wallet_id) VALUES (3, 3);
INSERT INTO app_user_wallets (user_user_id, wallets_wallet_id) VALUES (4, 4);
INSERT INTO app_user_wallets (user_user_id, wallets_wallet_id) VALUES (5, 5);
INSERT INTO app_user_wallets (user_user_id, wallets_wallet_id) VALUES (6, 6);
INSERT INTO app_user_wallets (user_user_id, wallets_wallet_id) VALUES (7, 7);

