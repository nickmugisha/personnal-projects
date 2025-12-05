-- src/main/resources/data.sql
-- Insert initial carburant types
INSERT IGNORE INTO carburants (type, description) VALUES 
('ESSENCE', 'Essence pour voitures et motos'),
('MAZOUT', 'Mazout pour groupes électrogènes');

-- Insert initial admin user (password: admin123)
INSERT IGNORE INTO users (name, email, password, role) VALUES 
('Administrator', 'admin@university.com', '$2a$10$ABCDE12345FGHIJ67890KLMNOPQRSTUVWXYZ0123456789ABCDEF', 'ADMIN');

-- Insert admin "prof" with encoded password
INSERT IGNORE INTO users (name, email, password, role) VALUES 
('prof', 'prof@university.com', '$2a$10$Ts0HwZLOgpDLr/QLHBDASOoGhqPTOzOtXZ2Ym7ThgzykbjNwXl1L6', 'ADMIN');

-- Insert initial stock
INSERT IGNORE INTO stock_carburant (carburant_id, quantite, date_mise_a_jour) 
SELECT id, 1000.0, NOW() FROM carburants WHERE type = 'ESSENCE';

INSERT IGNORE INTO stock_carburant (carburant_id, quantite, date_mise_a_jour) 
SELECT id, 2000.0, NOW() FROM carburants WHERE type = 'MAZOUT';