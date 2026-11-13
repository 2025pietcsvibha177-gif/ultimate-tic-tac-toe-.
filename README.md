# 🎮 Ultimate Tic Tac Toe

A Java Swing-based Tic Tac Toe game with MySQL database integration for player profiles, real-time score tracking, and match history.

---

## 🛠️ Requirements
- **Java JDK**: 17 or higher
- **Apache Maven**: 3.8+
- **MySQL Server**: 8.0+ running on port `3306`

---

## 🗄️ Database Setup (`database.sql`)

The database script is located at [`database.sql`](file:///c:/Users/DELL/tic%20tac%20toe/Tic%20Tac%20Toe/database.sql).

### Credentials in `DBConnection.java`:
- **Database URL**: `jdbc:mysql://localhost:3306/tictactoe_db`
- **Username**: `root`
- **Password**: `Vibha@11`

> **Note:** The application automatically verifies and creates the required tables (`players` and `games`) on startup via `DBConnection.initializeDatabase()`.

---

## 🔍 How to Test the MySQL Connection

Run either command below:

### Option A (Simplest - no quotes needed):
```bash
mvn exec:java -Ptest-db
```
or double-click:
```cmd
test-db.bat
```

### Option B (PowerShell syntax with quotes):
```powershell
mvn compile exec:java "-Dexec.mainClass=DBConnection"
```

---

## 🚀 How to Run the Game

```powershell
mvn clean compile exec:java
```
or double-click `run.bat`.
