-- =======================================================
-- Database Setup Script for Ultimate Tic Tac Toe Game
-- =======================================================

-- 1. Create database if not exists
CREATE DATABASE IF NOT EXISTS tictactoe_db;
USE tictactoe_db;

-- 2. Create 'players' table
CREATE TABLE IF NOT EXISTS players (
    player_id INT NOT NULL AUTO_INCREMENT,
    player_name VARCHAR(100) NOT NULL,
    symbol VARCHAR(10) NOT NULL,
    score INT DEFAULT 0,
    PRIMARY KEY (player_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3. Create 'games' table
CREATE TABLE IF NOT EXISTS games (
    game_id INT NOT NULL AUTO_INCREMENT,
    player1_id INT NOT NULL,
    player2_id INT NOT NULL,
    winner_id INT DEFAULT NULL,
    game_status VARCHAR(20) NOT NULL,
    played_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (game_id),
    KEY idx_player1 (player1_id),
    KEY idx_player2 (player2_id),
    KEY idx_winner (winner_id),
    CONSTRAINT fk_games_player1 FOREIGN KEY (player1_id) REFERENCES players (player_id) ON DELETE CASCADE,
    CONSTRAINT fk_games_player2 FOREIGN KEY (player2_id) REFERENCES players (player_id) ON DELETE CASCADE,
    CONSTRAINT fk_games_winner FOREIGN KEY (winner_id) REFERENCES players (player_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =======================================================
-- Useful Diagnostic & Verification Queries
-- =======================================================

-- View all registered players and their scores:
-- SELECT * FROM players ORDER BY score DESC, player_id DESC;

-- View match history with player names:
-- SELECT 
--     g.game_id,
--     p1.player_name AS player1,
--     p2.player_name AS player2,
--     COALESCE(w.player_name, 'DRAW') AS winner,
--     g.game_status,
--     g.played_at
-- FROM games g
-- JOIN players p1 ON g.player1_id = p1.player_id
-- JOIN players p2 ON g.player2_id = p2.player_id
-- LEFT JOIN players w ON g.winner_id = w.player_id
-- ORDER BY g.game_id DESC;
