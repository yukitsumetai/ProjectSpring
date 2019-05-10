SET NAMES utf8;

SET character_set_client = utf8mb4;

CREATE TABLE `tariffs`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `isValid`     tinyint(4)  NOT NULL,
    `name`        varchar(30) NOT NULL,
    `price`       double      NOT NULL,
    `isPromoted`  bit(1)      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 110041
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `options`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `description`  varchar(255) DEFAULT NULL,
    `isValid`      tinyint(4)      NOT NULL,
    `name`         varchar(30) NOT NULL,
    `priceMonthly` double      NOT NULL,
    `priceOneTime` double      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 220083
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `option_groups`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `isValid`     tinyint(4)   NOT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 330013
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `tariffs_options`
(
    `tariff_id` int(11) NOT NULL,
    `option_id` int(11) NOT NULL,
    PRIMARY KEY (`tariff_id`, `option_id`),
    KEY `FK5ab3bukhei65dhxcq9x74bkod` (`option_id`),
    CONSTRAINT `FK5ab3bukhei65dhxcq9x74bkod` FOREIGN KEY (`option_id`) REFERENCES `options` (`id`),
    CONSTRAINT `FK84724jygtdpwv7xf368ekwwgo` FOREIGN KEY (`tariff_id`) REFERENCES `tariffs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `options_options`
(
    `parent_id` int(11) DEFAULT NULL,
    `child_id`  int(11) NOT NULL,
    PRIMARY KEY (`child_id`),
    KEY `FK9i55rwfhcvit9oby3pds63dgh` (`parent_id`),
    CONSTRAINT `FK9i55rwfhcvit9oby3pds63dgh` FOREIGN KEY (`parent_id`) REFERENCES `options` (`id`),
    CONSTRAINT `FKa36uv04jwm7c1vnjldnqpt6vr` FOREIGN KEY (`child_id`) REFERENCES `options` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `options_optionGroups`
(
    `group_id`  int(11) DEFAULT NULL,
    `option_id` int(11) NOT NULL,
    PRIMARY KEY (`option_id`),
    KEY `FK9g0aqso2kv32sdsq4nsp2miqs` (`group_id`),
    CONSTRAINT `FK9g0aqso2kv32sdsq4nsp2miqs` FOREIGN KEY (`group_id`) REFERENCES `option_groups` (`id`),
    CONSTRAINT `FKh4mts4h1ga6l16deehiwo2n0t` FOREIGN KEY (`option_id`) REFERENCES `options` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `free_phone_numbers`
(
    `phoneNumber` bigint(13) NOT NULL,
    PRIMARY KEY (`phoneNumber`),
    UNIQUE KEY `phoneNumber_UNIQUE` (`phoneNumber`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `addresses`
(
    `id`      int(11) NOT NULL,
    `city`    varchar(255) DEFAULT NULL,
    `country` varchar(255) DEFAULT NULL,
    `houseNo` varchar(255) DEFAULT NULL,
    `street`  varchar(255) DEFAULT NULL,
    `zip`     int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `users`
(
    `id`       bigint(20) NOT NULL,
    `password` varchar(255) DEFAULT NULL,
    `role`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `clients`
(
    `id`         int(11) NOT NULL,
    `birthday`   varchar(255) DEFAULT NULL,
    `email`      varchar(255) DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    `passport`   int(11)      DEFAULT NULL,
    `surname`    varchar(10)  DEFAULT NULL,
    `address_id` int(11)      DEFAULT NULL,
    `user_id`    bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK21gyuophuha3vq8t1os4x2jtl` (`address_id`),
    KEY `FKtiuqdledq2lybrds2k3rfqrv4` (`user_id`),
    CONSTRAINT `FK21gyuophuha3vq8t1os4x2jtl` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
    CONSTRAINT `FKtiuqdledq2lybrds2k3rfqrv4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `contracts`
(
    `phoneNumber` bigint(13)  NOT NULL,
    `agentBlock`  bit(1)         NOT NULL,
    `blocked`     bit(1)         NOT NULL,
    `price`       double  DEFAULT NULL,
    `clientId`    int(11) DEFAULT NULL,
    `tariffId`    int(11) DEFAULT NULL,
    PRIMARY KEY (`phoneNumber`),
    KEY `FK8b0gy743nmgosbhtr4gbxnd5o` (`clientId`),
    KEY `FK6w7nevrgu5i7qt32dmch26je6` (`tariffId`),
    CONSTRAINT `FK6w7nevrgu5i7qt32dmch26je6` FOREIGN KEY (`tariffId`) REFERENCES `tariffs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `FK8b0gy743nmgosbhtr4gbxnd5o` FOREIGN KEY (`clientId`) REFERENCES `clients` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `users_contracts`
(
    `user_id`     bigint(20)     NOT NULL,
    `phoneNumber` bigint(13) NOT NULL,
    UNIQUE KEY `UK_jmqesbhhlewj8bw0jixpqs9n` (`phoneNumber`),
    KEY `FKgbrkwd1vp6vc9qxdfdp3uc7fj` (`user_id`),
    CONSTRAINT `FKgbrkwd1vp6vc9qxdfdp3uc7fj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKqdnc4dn7dxa5734hvobqnwehe` FOREIGN KEY (`phoneNumber`) REFERENCES `contracts` (`phoneNumber`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `contracts_options`
(
    `Contract_phoneNumber` bigint(13) NOT NULL,
    `options_id`           int(11)        NOT NULL,
    PRIMARY KEY (`Contract_phoneNumber`, `options_id`),
    KEY `FK78x35p7lndruhco5qccp48p3c` (`options_id`),
    CONSTRAINT `FK2i0gjbn44q63y3oow4h07o5be` FOREIGN KEY (`Contract_phoneNumber`) REFERENCES `contracts` (`phoneNumber`),
    CONSTRAINT `FK78x35p7lndruhco5qccp48p3c` FOREIGN KEY (`options_id`) REFERENCES `options` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint(20) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
