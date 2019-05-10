LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses`
    DISABLE KEYS */;
INSERT INTO `addresses`
VALUES (2, 'Lauf an der Pegnitz', 'Germany', '7', 'Roderweg', 35006),
       (5, 'Nurnberg', 'Germany', '19', 'Schillerplatz', 97100),
       (8, 'Lemgo', 'Germany', '6', 'Heinegasse', 93220),
       (11, 'Haan', 'Germany', '66', 'Kellnerstrasse', 45880),
       (14, 'Bonn', 'Germany', '20', 'Baumschulallee', 53115),
       (17, 'Winsen', 'Germany', '22', 'Kunzestrasse', 33013),
       (20, 'Wulfrath', 'Germany', '81', 'Michel-Klaus-Ring', 91547),
       (23, 'Oberursel', 'Germany', '210', 'Munchstrasse', 13833),
       (26, 'Stuttgart', 'Germany', '40', 'Ollenhauer Str', 68576),
       (29, 'Duderstadt', 'Germany', '12', 'Petra-Bittnerstrasse', 76546),
       (32, 'Ansbach', 'Germany', '92', 'Isabelle-Bohme-Ring', 98126),
       (35, 'Finsterwalde', 'Germany', '15', 'Antje-Wendt-Allee', 64680),
       (38, 'Emmendingen', 'Germany', '5', 'Christel-Walher-Ring', 68667),
       (41, 'Laupheim', 'Germany', '3', 'Schulteplatz', 63057),
       (44, 'Prenzlau', 'Germany', '16', 'Kuhnstr', 65675);
/*!40000 ALTER TABLE `addresses`
    ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES (3, '$2a$10$5ZVUHWKd6YrfAAW5OUNiI.qFsDtsfierv5F30jh/sbOuh.plgAUSW', 'ROLE_USER'),
       (4, '$2a$10$5ZVUHWKd6YrfAAW5OUNiI.qFsDtsfierv5F30jh/sbOuh.plgAUSW', 'ROLE_ADMIN'),
       (6, '$2a$10$Nkf.V6Az8alH0SSEbhBJE.S8Lsksm0hcD0vgGFysr3SPmFf5aWY7m', 'ROLE_USER'),
       (9, '$2a$10$QG989d7xpBKetMGFyV7NuuY3Y44ELQG1BTQYnaiulpIqn5JFInAji', 'ROLE_USER'),
       (12, '$2a$10$.DQCkTIR5/1lmtRWbdPiKey8NrXjQ8vIwdivY6tjEIqKYuwqNIQhe', 'ROLE_USER'),
       (15, '$2a$10$Z2SAbEwbWK2f9APaZ98Zd.jo0rEPGOZE1AUwjU5F/ljsee0.3pDnC', 'ROLE_USER'),
       (18, '$2a$10$uEq56pd/tX.NElNJi.lCo.hoLgP5Xsaqdx6oNe4msGcrgLuGEhgDm', 'ROLE_USER'),
       (21, '$2a$10$Vq1VipHQTXXtQ.9GgzaaUOUuiD3rKb7lBbm2uOwjmwWAo01Gc6qUy', 'ROLE_USER'),
       (24, '$2a$10$37xU4IH4he2AcZQ5PndBpuYN2m6ECBjkeaPHcuvxkML2jZHhUp.AS', 'ROLE_USER'),
       (27, '$2a$10$unuG0wPf8vvDHM1twmJlnO7M5.AZ7tiHBme5ir5oXMuZviqf4K8ha', 'ROLE_USER'),
       (30, '$2a$10$DhJt0gTtxu4vwoGou/KV1OnBWplAyj1kh3XkcRmcsStj7ZdrjbmRi', 'ROLE_USER'),
       (33, '$2a$10$IU38kyyqIPR8RSJZ0Mb57.rEwfbcjiF5DLUJFtdFmYB1ju0W94uZG', 'ROLE_USER'),
       (36, '$2a$10$C9DCKlL8TJjUInve7XcXXufPVQyHrfRjsbwaC5waiR26h7/CO0xsi', 'ROLE_USER'),
       (39, '$2a$10$oWazRzwN0yD8pIEmn1GJMOiHDsnaCVJFRb/e02JVvxxjkTgTwY8Uy', 'ROLE_USER'),
       (42, '$2a$10$MhfskDmUDx1K3ygnV.sgOeOUhyNX10i1opnlcND3HfOAuH4xbNEA.', 'ROLE_USER'),
       (45, '$2a$10$SuJ8TAhCN5lv7pgDLb4p1OFiSN6uS34TL4euBwu0s3jEYJLHLJ0VC', 'ROLE_USER');
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients`
    DISABLE KEYS */;
INSERT INTO `clients`
VALUES (1, '1958-03-12', 'ajubb0@hatena.ne.jp', 'Jakob', 95367454, 'Jubb', 2, 3),
       (4, '1936-02-13', 'kblincow1@cafepress.com', 'Elena', 89675695, 'Stoff', 5, 6),
       (7, '1931-02-01', 'rcoggins2@cdc.gov', 'Selene', 57678696, 'Coggins', 8, 9),
       (10, '1959-07-21', 'lclaw3@nba.com', 'Cloe', 42160157, 'Kurz', 11, 12),
       (13, '1990-02-02', 'clark@tfr.com', 'Clark', 78945612, 'Kent', 14, 15),
       (16, '1945-05-17', 'csieghard5@ucoz.ru', 'Yenora', 92292797, 'Sieghard', 17, 18),
       (19, '1984-04-30', 'agrane6@unicef.org', 'Kevina', 63737076, 'Grane', 20, 21),
       (22, '2004-07-03', 'pdumingo7@feedburner.com', 'Esteve', 93435682, 'Dumingo', 23, 24),
       (25, '1954-10-27', 'bchauvey9@vk.com', 'Regine', 31472027, 'Becker', 26, 27),
       (28, '1981-10-11', 'bdranfielda@who.int', 'Josee', 81463350, 'Dranfield', 29, 30),
       (31, '1993-08-13', 'ldecourtneyk@myspace.com', 'Francoise', 63269855, 'Falash', 32, 33),
       (34, '1982-06-30', 'brueg@com.com', 'Asa', 14809594, 'Rue', 35, 36),
       (37, '1956-09-27', 'ftetther4@fc2.com', 'Esbjorn', 75901087, 'Tutt', 38, 39),
       (40, '1973-12-31', 'pmcbean8@opensource.org', 'Ines', 57121897, 'Stimm', 41, 42),
       (43, '1956-08-09', 'mlackies@uol.com.br', 'Goran', 21885134, 'Pflieger', 44, 45);
/*!40000 ALTER TABLE `clients`
    ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts`
    DISABLE KEYS */;
INSERT INTO `contracts`
VALUES (1111, _binary '\0', _binary '\0', NULL, NULL, NULL),
       (4917620020488, _binary '\0', _binary '\0', 47.97, 1, 110001),
       (4917620024145, _binary '\0', _binary '\0', 69.97, 4, 110002),
       (4917620027159, _binary '\0', _binary '\0', 12.98, 7, 110015),
       (4917620033861, _binary '\0', _binary '\0', 52.47, 1, 110012),
       (4917620042786, _binary '\0', _binary '\0', 10.98, 10, 110014),
       (4917620044240, _binary '\0', _binary '\0', 32.98, 16, 110006),
       (4917620093100, _binary '\0', _binary '\0', 11.99, 13, 110005),
       (4917620105090, _binary '\0', _binary '\0', 8.98, 19, 110014),
       (4917620116138, _binary '\0', _binary '\0', 67.98, 22, 110003),
       (4917620157260, _binary '', _binary '', 10.99, 25, 110005),
       (4917620160894, _binary '\0', _binary '\0', 10, 28, 110006),
       (4917620170472, _binary '\0', _binary '\0', 8.98, 31, 110014),
       (4917620254462, _binary '\0', _binary '\0', 7, 34, 110005),
       (4917620260271, _binary '\0', _binary '\0', 14.99, 37, 110010),
       (4917620263317, _binary '\0', _binary '\0', 49.99, 19, 110002),
       (4917620269580, _binary '', _binary '', 7, 10, 110005),
       (4917620273221, _binary '\0', _binary '\0', 77.96, 40, 110003),
       (4917620301630, _binary '\0', _binary '\0', 10, 22, 110005),
       (4917620341911, _binary '\0', _binary '\0', 29.99, 43, 110007),
       (4917620348340, _binary '\0', _binary '\0', 16.99, 10, 110005),
       (4917620366388, _binary '\0', _binary '\0', 63.98, 22, 110003),
       (4917620370704, _binary '\0', _binary '\0', 13.99, 4, 110006),
       (4917620396511, _binary '\0', _binary '\0', 22.99, 43, 110005);
/*!40000 ALTER TABLE `contracts`
    ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `users_contracts` WRITE;
/*!40000 ALTER TABLE `users_contracts`
    DISABLE KEYS */;
INSERT INTO `users_contracts`
VALUES (3, 4917620020488),
       (3, 4917620033861),
       (4, 1111),
       (6, 4917620024145),
       (6, 4917620370704),
       (9, 4917620027159),
       (12, 4917620042786),
       (12, 4917620269580),
       (12, 4917620348340),
       (15, 4917620093100),
       (18, 4917620044240),
       (21, 4917620105090),
       (21, 4917620263317),
       (24, 4917620116138),
       (24, 4917620301630),
       (24, 4917620366388),
       (27, 4917620157260),
       (30, 4917620160894),
       (33, 4917620170472),
       (36, 4917620254462),
       (39, 4917620260271),
       (42, 4917620273221),
       (45, 4917620341911),
       (45, 4917620396511);
/*!40000 ALTER TABLE `users_contracts`
    ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `contracts_options` WRITE;
/*!40000 ALTER TABLE `contracts_options`
    DISABLE KEYS */;
INSERT INTO `contracts_options`
VALUES (4917620044240, 220001),
       (4917620044240, 220002),
       (4917620269580, 220003),
       (4917620341911, 220003),
       (4917620396511, 220003),
       (4917620269580, 220004),
       (4917620341911, 220004),
       (4917620093100, 220005),
       (4917620301630, 220005),
       (4917620093100, 220007),
       (4917620301630, 220007),
       (4917620020488, 220011),
       (4917620273221, 220012),
       (4917620027159, 220013),
       (4917620042786, 220013),
       (4917620105090, 220013),
       (4917620020488, 220014),
       (4917620170472, 220014),
       (4917620024145, 220015),
       (4917620033861, 220015),
       (4917620044240, 220015),
       (4917620093100, 220015),
       (4917620269580, 220015),
       (4917620273221, 220015),
       (4917620341911, 220015),
       (4917620348340, 220015),
       (4917620366388, 220015),
       (4917620396511, 220015),
       (4917620033861, 220016),
       (4917620341911, 220016),
       (4917620273221, 220017),
       (4917620341911, 220017),
       (4917620024145, 220018),
       (4917620033861, 220018),
       (4917620044240, 220018),
       (4917620348340, 220018),
       (4917620024145, 220019),
       (4917620093100, 220019),
       (4917620116138, 220020),
       (4917620273221, 220021),
       (4917620341911, 220021),
       (4917620024145, 220023),
       (4917620033861, 220023),
       (4917620116138, 220023),
       (4917620273221, 220024),
       (4917620366388, 220024),
       (4917620024145, 220026),
       (4917620116138, 220026),
       (4917620273221, 220027),
       (4917620396511, 220028),
       (4917620042786, 220029),
       (4917620116138, 220029),
       (4917620170472, 220029),
       (4917620301630, 220030),
       (4917620105090, 220033),
       (4917620170472, 220033),
       (4917620027159, 220036),
       (4917620170472, 220036),
       (4917620024145, 220038),
       (4917620157260, 220040),
       (4917620370704, 220040),
       (4917620044240, 220042),
       (4917620396511, 220043);
/*!40000 ALTER TABLE `contracts_options`
    ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence`
    DISABLE KEYS */;
INSERT INTO `hibernate_sequence`
VALUES (46),
       (46),
       (46);
/*!40000 ALTER TABLE `hibernate_sequence`
    ENABLE KEYS */;
UNLOCK TABLES;
