-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 07 nov. 2025 à 07:00
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `carburant_universitaire`
--

-- --------------------------------------------------------

--
-- Structure de la table `approvisionnements`
--

DROP TABLE IF EXISTS `approvisionnements`;
CREATE TABLE IF NOT EXISTS `approvisionnements` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_approvisionnement` datetime(6) DEFAULT NULL,
  `numero_bon` varchar(255) DEFAULT NULL,
  `quantite` double DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `carburant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKppjuj7sb19l2iu48cca7si9ih` (`carburant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `carburants`
--

DROP TABLE IF EXISTS `carburants`;
CREATE TABLE IF NOT EXISTS `carburants` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdijocu8wcxs0wphblr8983o1r` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `carburants`
--

INSERT INTO `carburants` (`id`, `description`, `type`) VALUES
(1, 'Essence pour voitures et motos', 'ESSENCE'),
(2, 'Essence pour trucs and lorries', 'MAZOUT');

-- --------------------------------------------------------

--
-- Structure de la table `demandes_carburant`
--

DROP TABLE IF EXISTS `demandes_carburant`;
CREATE TABLE IF NOT EXISTS `demandes_carburant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_demande` datetime(6) DEFAULT NULL,
  `motif` varchar(255) DEFAULT NULL,
  `quantite_demandee` double DEFAULT NULL,
  `raison_rejet` varchar(255) DEFAULT NULL,
  `statut` enum('APPROUVEE','EN_ATTENTE_CHEF','EN_ATTENTE_DAF','EN_ATTENTE_RESPONSABLE','REJETEE') DEFAULT NULL,
  `validation_chef` bit(1) DEFAULT NULL,
  `validationdaf` bit(1) DEFAULT NULL,
  `validation_responsable` bit(1) DEFAULT NULL,
  `chauffeur_id` bigint DEFAULT NULL,
  `engin_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKodd5c9kl8ae11p469c37d5c7k` (`chauffeur_id`),
  KEY `FK2j8r4qljmtvaft3lp4nbx1e1v` (`engin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `demandes_carburant`
--

INSERT INTO `demandes_carburant` (`id`, `date_demande`, `motif`, `quantite_demandee`, `raison_rejet`, `statut`, `validation_chef`, `validationdaf`, `validation_responsable`, `chauffeur_id`, `engin_id`) VALUES
(1, NULL, 'Mission administrative à la ville', 50, NULL, 'EN_ATTENTE_CHEF', b'0', b'0', b'0', 1, 1),
(2, NULL, 'Mission administrative à la ville', 50, NULL, 'APPROUVEE', b'1', b'1', b'1', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `engins`
--

DROP TABLE IF EXISTS `engins`;
CREATE TABLE IF NOT EXISTS `engins` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `consommation_moyenne` double DEFAULT NULL,
  `plaque` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `carburant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnh8w97i73ivpominbe8pay43` (`carburant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `engins`
--

INSERT INTO `engins` (`id`, `consommation_moyenne`, `plaque`, `type`, `carburant_id`) VALUES
(1, 8.5, 'UNIV-001', 'Voiture', 1);

-- --------------------------------------------------------

--
-- Structure de la table `stock_carburant`
--

DROP TABLE IF EXISTS `stock_carburant`;
CREATE TABLE IF NOT EXISTS `stock_carburant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_miseajour` datetime(6) DEFAULT NULL,
  `quantite` double DEFAULT NULL,
  `carburant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbjd2nguqb3nnbmyjxf21dp2tf` (`carburant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `stock_carburant`
--

INSERT INTO `stock_carburant` (`id`, `date_miseajour`, `quantite`, `carburant_id`) VALUES
(1, '2025-11-06 11:43:52.624483', 950, 1);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CHAUFFEUR','CHEF_CHALOI','DAF','RESPONSABLE_CARBURANT') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `password`, `role`) VALUES
(1, 'prof@university.com', 'prof', '$2a$10$rRviObeGgQlhZi0tuowUceN.BExSNmV/0RB8OdgrKBQI9rHAIX0bu', 'ADMIN'),
(2, 'chauffeur@university.com', 'Jean Chauffeur', '$2a$10$nLzGtQNeKlYGs4YVflL2HO2QK6LEvxuxFujZUOlZ1aLWxbM4xBJAS', 'CHAUFFEUR'),
(3, 'chef@university.com', 'Paul Chef', '$2a$10$TZWzGETpQ02uNVCeR8OkV.6kDygaN4XEjrigRN/G/1wmBeRahx/gu', 'CHEF_CHALOI'),
(4, 'daf@university.com', 'David DAF', '$2a$10$a3T//DQ7lrpcALcIqO0VYOdDpLxjSXOhIM9HYKs/mI45bxguXRy7G', 'DAF'),
(5, 'responsable@university.com', 'Marie Responsable', '$2a$10$3zlt1I.KerFq/uBhkX3BUOoRB/K8SXkSqQ5vAIKf4nnjrcCPuZtbm', 'RESPONSABLE_CARBURANT');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `approvisionnements`
--
ALTER TABLE `approvisionnements`
  ADD CONSTRAINT `FKppjuj7sb19l2iu48cca7si9ih` FOREIGN KEY (`carburant_id`) REFERENCES `carburants` (`id`);

--
-- Contraintes pour la table `demandes_carburant`
--
ALTER TABLE `demandes_carburant`
  ADD CONSTRAINT `FK2j8r4qljmtvaft3lp4nbx1e1v` FOREIGN KEY (`engin_id`) REFERENCES `engins` (`id`),
  ADD CONSTRAINT `FKodd5c9kl8ae11p469c37d5c7k` FOREIGN KEY (`chauffeur_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `engins`
--
ALTER TABLE `engins`
  ADD CONSTRAINT `FKqnh8w97i73ivpominbe8pay43` FOREIGN KEY (`carburant_id`) REFERENCES `carburants` (`id`);

--
-- Contraintes pour la table `stock_carburant`
--
ALTER TABLE `stock_carburant`
  ADD CONSTRAINT `FKbjd2nguqb3nnbmyjxf21dp2tf` FOREIGN KEY (`carburant_id`) REFERENCES `carburants` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
