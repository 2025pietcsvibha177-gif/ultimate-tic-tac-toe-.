@echo off
echo ===================================================
echo              TICTACTOE_DB: TABLES
echo ===================================================
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pVibha@11 -e "USE tictactoe_db; SHOW TABLES;"
echo.
echo ===================================================
echo             PLAYERS TABLE (SCORES)
echo ===================================================
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pVibha@11 -e "USE tictactoe_db; SELECT * FROM players ORDER BY player_id DESC;"
echo.
echo ===================================================
echo             GAMES TABLE (MATCH HISTORY)
echo ===================================================
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pVibha@11 -e "USE tictactoe_db; SELECT g.game_id, p1.player_name AS Player1, p2.player_name AS Player2, COALESCE(w.player_name, 'DRAW') AS Winner, g.game_status AS Status, g.played_at AS PlayedAt FROM games g JOIN players p1 ON g.player1_id = p1.player_id JOIN players p2 ON g.player2_id = p2.player_id LEFT JOIN players w ON g.winner_id = w.player_id ORDER BY g.game_id DESC LIMIT 10;"
echo.
pause
