import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Random;

public class TicTacToe extends JFrame {

    final Color BG = new Color(10,15,40);
    final Color CELL = new Color(25,32,70);
    final Color CYAN = new Color(40,220,255);
    final Color PINK = new Color(255,80,180);
    final Color PURPLE = new Color(125,80,240);
    final Color GOLD = new Color(255,200,40);
    final Color GREEN = new Color(60,230,140);
    final Color RED = new Color(255,70,80);
    final Color WHITE = Color.WHITE;

    JButton[][] cells = new JButton[3][3];

    String player1, player2;
    String symbol1, symbol2;
    String currentPlayer, currentSymbol;

    int score1 = 0;
    int score2 = 0;

    int player1Id;
    int player2Id;

    boolean gameOver = false;

    JLabel turnLabel;
    JLabel scoreLabel;

    public TicTacToe() {

        setTitle("🎮 ULTIMATE TIC TAC TOE 🎮");
        setSize(900,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        showStart();
        setVisible(true);
    }

    // ================= LABEL =================

    JLabel label(String s, int z, Color c) {

        JLabel l = new JLabel(s);

        l.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.BOLD,
                        z
                )
        );

        l.setForeground(c);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        return l;
    }

    // ================= BUTTON =================

    JButton button(String s, int z) {

        JButton b = new JButton(s);

        b.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.BOLD,
                        z
                )
        );

        b.setForeground(WHITE);
        b.setBackground(PURPLE);
        b.setFocusPainted(false);

        b.setBorder(
                BorderFactory.createLineBorder(
                        CYAN,
                        2
                )
        );

        return b;
    }

    // ================= START SCREEN =================

    void showStart() {

        JPanel p = new JPanel();

        p.setLayout(
                new BoxLayout(
                        p,
                        BoxLayout.Y_AXIS
                )
        );

        p.setBackground(BG);

        p.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        60,
                        30,
                        60
                )
        );

        p.add(
                label(
                        "🎮 ⭐ 🏆 ⭐ 🎮",
                        40,
                        GOLD
                )
        );

        p.add(
                label(
                        "TIC • TAC • TOE",
                        45,
                        CYAN
                )
        );

        p.add(
                label(
                        "🔥 ULTIMATE 2 PLAYER BATTLE 🔥",
                        20,
                        PINK
                )
        );

        p.add(
                Box.createVerticalStrut(25)
        );

        JTextField f1 = new JTextField();
        JTextField f2 = new JTextField();

        f1.setMaximumSize(
                new Dimension(600,45)
        );

        f2.setMaximumSize(
                new Dimension(600,45)
        );

        f1.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        f2.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        f1.setHorizontalAlignment(
                JTextField.CENTER
        );

        f2.setHorizontalAlignment(
                JTextField.CENTER
        );

        f1.setBorder(
                BorderFactory.createTitledBorder(
                        "💙 Player 1 Name"
                )
        );

        f2.setBorder(
                BorderFactory.createTitledBorder(
                        "💗 Player 2 Name"
                )
        );

        p.add(f1);

        p.add(
                Box.createVerticalStrut(15)
        );

        p.add(f2);

        p.add(
                Box.createVerticalStrut(25)
        );

        p.add(
                label(
                        "🎯 CHOOSE • ⚡ PLAY • 🏆 WIN • 🎉 CELEBRATE",
                        17,
                        GOLD
                )
        );

        p.add(
                Box.createVerticalStrut(20)
        );

        JButton start =
                button(
                        "🚀🎮 START GAME 🎮🚀",
                        22
                );

        start.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        start.addActionListener(e -> {

            player1 = f1.getText().trim();
            player2 = f2.getText().trim();

            if(player1.isEmpty())
                player1 = "Player 1";

            if(player2.isEmpty())
                player2 = "Player 2";

            chooseSymbol();
        });

        p.add(start);

        p.add(
                Box.createVerticalStrut(20)
        );

        p.add(
                label(
                        "😎 🔥 LET THE BATTLE BEGIN! 🔥 😎",
                        19,
                        WHITE
                )
        );

        setContentPane(p);

        revalidate();
        repaint();
    }

    // ================= CHOOSE SYMBOL =================

    void chooseSymbol() {

        JDialog d =
                new JDialog(
                        this,
                        "❌⭕ CHOOSE SYMBOL",
                        true
                );

        d.setSize(650,500);
        d.setLocationRelativeTo(this);

        JPanel p = new JPanel();

        p.setLayout(
                new BoxLayout(
                        p,
                        BoxLayout.Y_AXIS
                )
        );

        p.setBackground(BG);

        p.add(
                label(
                        "✨ 🎮 ⚡ 🏆 ⚡ 🎮 ✨",
                        32,
                        GOLD
                )
        );

        p.add(
                label(
                        "🎯 " + player1 +
                                ", CHOOSE YOUR POWER!",
                        25,
                        WHITE
                )
        );

        p.add(
                Box.createVerticalStrut(20)
        );

        JPanel q =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                25,
                                0
                        )
                );

        q.setBackground(BG);

        JButton x =
                button(
                        "❌ X ⚡ POWER X",
                        25
                );

        JButton o =
                button(
                        "⭕ O 💗 POWER O",
                        25
                );

        q.add(x);
        q.add(o);

        p.add(q);

        x.addActionListener(e -> {

            symbol1 = "X";
            symbol2 = "O";

            d.dispose();

            savePlayers();
        });

        o.addActionListener(e -> {

            symbol1 = "O";
            symbol2 = "X";

            d.dispose();

            savePlayers();
        });

        d.setContentPane(p);

        d.setVisible(true);
    }

    // ================= SAVE PLAYERS =================

    void savePlayers() {

        try {

            Connection con =
                    DBConnection.getConnection();

            if(con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection failed!"
                );

                return;
            }

            String sql =
                    "INSERT INTO players " +
                            "(player_name, symbol, score) " +
                            "VALUES (?, ?, 0)";

            PreparedStatement ps =
                    con.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            // Player 1

            ps.setString(
                    1,
                    player1
            );

            ps.setString(
                    2,
                    symbol1
            );

            ps.executeUpdate();

            ResultSet rs =
                    ps.getGeneratedKeys();

            if(rs.next()) {

                player1Id =
                        rs.getInt(1);
            }

            rs.close();
            ps.close();

            // Player 2

            ps =
                    con.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setString(
                    1,
                    player2
            );

            ps.setString(
                    2,
                    symbol2
            );

            ps.executeUpdate();

            rs =
                    ps.getGeneratedKeys();

            if(rs.next()) {

                player2Id =
                        rs.getInt(1);
            }

            rs.close();
            ps.close();

            con.close();

            score1 = 0;
            score2 = 0;

            firstPlayer();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= FIRST PLAYER =================

    void firstPlayer() {

        JDialog d =
                new JDialog(
                        this,
                        "👑 FIRST TURN",
                        true
                );

        d.setSize(650,450);
        d.setLocationRelativeTo(this);

        JPanel p = new JPanel();

        p.setLayout(
                new BoxLayout(
                        p,
                        BoxLayout.Y_AXIS
                )
        );

        p.setBackground(BG);

        p.add(
                label(
                        "👑 ⚡ 🎯 ⚡ 👑",
                        35,
                        GOLD
                )
        );

        p.add(
                label(
                        "🔥 WHO PLAYS FIRST? 🔥",
                        28,
                        WHITE
                )
        );

        p.add(
                Box.createVerticalStrut(20)
        );

        JButton a =
                button(
                        "💙 " +
                                player1 +
                                " • " +
                                symbol1,
                        24
                );

        JButton b =
                button(
                        "💗 " +
                                player2 +
                                " • " +
                                symbol2,
                        24
                );

        JPanel q =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );

        q.setBackground(BG);

        q.add(a);
        q.add(b);

        p.add(q);

        a.addActionListener(e -> {

            currentPlayer = player1;
            currentSymbol = symbol1;

            d.dispose();

            game();

            speak(
                    player1 +
                            ", your turn."
            );
        });

        b.addActionListener(e -> {

            currentPlayer = player2;
            currentSymbol = symbol2;

            d.dispose();

            game();

            speak(
                    player2 +
                            ", your turn."
            );
        });

        d.setContentPane(p);

        d.setVisible(true);
    }

    // ================= GAME =================

    void game() {

        JPanel p =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        p.setBackground(BG);

        JPanel h = new JPanel();

        h.setLayout(
                new BoxLayout(
                        h,
                        BoxLayout.Y_AXIS
                )
        );

        h.setBackground(BG);

        h.add(
                label(
                        "🎮 🔥 🏆 🔥 🎮",
                        30,
                        GOLD
                )
        );

        h.add(
                label(
                        "🔥 TIC TAC TOE BATTLE 🔥",
                        30,
                        CYAN
                )
        );

        turnLabel =
                label(
                        "",
                        20,
                        WHITE
                );

        scoreLabel =
                label(
                        "",
                        16,
                        GOLD
                );

        h.add(turnLabel);
        h.add(scoreLabel);

        p.add(
                h,
                BorderLayout.NORTH
        );

        JPanel board =
                new JPanel(
                        new GridLayout(
                                3,
                                3,
                                8,
                                8
                        )
                );

        board.setBackground(BG);

        board.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        30,
                        15,
                        30
                )
        );

        for(int r=0;r<3;r++) {

            for(int c=0;c<3;c++) {

                JButton b =
                        new JButton("✨");

                b.setFont(
                        new Font(
                                "Segoe UI Emoji",
                                Font.BOLD,
                                34
                        )
                );

                b.setBackground(CELL);
                b.setFocusPainted(false);

                b.setBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        70,
                                        80,
                                        130
                                ),
                                2
                        )
                );

                int row = r;
                int col = c;

                b.addActionListener(
                        e ->
                                playMove(
                                        row,
                                        col
                                )
                );

                cells[r][c] = b;

                board.add(b);
            }
        }

        p.add(
                board,
                BorderLayout.CENTER
        );

        JPanel bot = new JPanel();

        bot.setBackground(BG);

        JButton newGame =
                button(
                        "🔄 NEW GAME",
                        14
                );

        JButton reset =
                button(
                        "🏆 RESET SCORE",
                        14
                );

        JButton history =
                button(
                        "📜 HISTORY",
                        14
                );

        JButton exit =
                button(
                        "🚪 EXIT",
                        14
                );

        newGame.addActionListener(
                e -> showStart()
        );

        reset.addActionListener(e -> {

            resetDatabaseScore();

            score1 = 0;
            score2 = 0;

            update();
        });

        history.addActionListener(
                e -> showHistory()
        );

        exit.addActionListener(
                e -> System.exit(0)
        );

        bot.add(newGame);
        bot.add(reset);
        bot.add(history);
        bot.add(exit);

        p.add(
                bot,
                BorderLayout.SOUTH
        );

        setContentPane(p);

        update();

        revalidate();
        repaint();
    }

    // ================= PLAY MOVE =================

    void playMove(int r,int c) {

        if(gameOver)
            return;

        JButton b =
                cells[r][c];

        if(!b.getText().equals("✨")) {

            b.setBackground(RED);

            speak(
                    "Invalid move. Choose another position."
            );

            Timer t =
                    new Timer(
                            300,
                            e ->
                                    b.setBackground(
                                            CELL
                                    )
                    );

            t.setRepeats(false);

            t.start();

            return;
        }

        b.setText(
                currentSymbol.equals("X")
                        ? "❌"
                        : "⭕"
        );

        b.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.BOLD,
                        60
                )
        );

        b.setForeground(
                currentSymbol.equals("X")
                        ? CYAN
                        : PINK
        );

        // WIN

        if(win()) {

            gameOver = true;

            int winnerId;

            if(currentPlayer.equals(player1)) {

                score1++;

                winnerId =
                        player1Id;

            } else {

                score2++;

                winnerId =
                        player2Id;
            }

            updateDatabaseScore(
                    winnerId
            );

            saveGame(
                    winnerId,
                    "WIN"
            );

            update();

            winner();

            return;
        }

        // DRAW

        if(draw()) {

            gameOver = true;

            saveGame(
                    null,
                    "DRAW"
            );

            drawPopup();

            return;
        }

        // CHANGE TURN

        if(currentPlayer.equals(player1)) {

            currentPlayer = player2;
            currentSymbol = symbol2;

        } else {

            currentPlayer = player1;
            currentSymbol = symbol1;
        }

        update();

        speak(
                currentPlayer +
                        ", your turn."
        );
    }

    // ================= WIN =================

    boolean win() {

        String s =
                currentSymbol.equals("X")
                        ? "❌"
                        : "⭕";

        for(int i=0;i<3;i++) {

            if(eq(
                    s,
                    cells[i][0],
                    cells[i][1],
                    cells[i][2]
            ))

                return flash(
                        cells[i][0],
                        cells[i][1],
                        cells[i][2]
                );

            if(eq(
                    s,
                    cells[0][i],
                    cells[1][i],
                    cells[2][i]
            ))

                return flash(
                        cells[0][i],
                        cells[1][i],
                        cells[2][i]
                );
        }

        if(eq(
                s,
                cells[0][0],
                cells[1][1],
                cells[2][2]
        ))

            return flash(
                    cells[0][0],
                    cells[1][1],
                    cells[2][2]
            );

        if(eq(
                s,
                cells[0][2],
                cells[1][1],
                cells[2][0]
        ))

            return flash(
                    cells[0][2],
                    cells[1][1],
                    cells[2][0]
            );

        return false;
    }

    // ================= EQUAL =================

    boolean eq(
            String s,
            JButton a,
            JButton b,
            JButton c
    ) {

        return a.getText().equals(s)
                && b.getText().equals(s)
                && c.getText().equals(s);
    }

    // ================= FLASH =================

    boolean flash(
            JButton a,
            JButton b,
            JButton c
    ) {

        a.setBackground(GOLD);
        b.setBackground(GOLD);
        c.setBackground(GOLD);

        Timer t =
                new Timer(
                        150,
                        e -> {

                            Color z =
                                    a.getBackground()
                                            .equals(GOLD)
                                            ? PINK
                                            : GOLD;

                            a.setBackground(z);
                            b.setBackground(z);
                            c.setBackground(z);
                        }
                );

        t.start();

        Timer stop =
                new Timer(
                        900,
                        e -> t.stop()
                );

        stop.setRepeats(false);
        stop.start();

        return true;
    }

    // ================= DRAW =================

    boolean draw() {

        for(JButton[] row : cells)

            for(JButton b : row)

                if(
                        b.getText()
                                .equals("✨")
                )

                    return false;

        return true;
    }

    // ================= WINNER =================

    void winner() {

        String w =
                currentPlayer;

        String l =
                w.equals(player1)
                        ? player2
                        : player1;

        speak(
                "Congratulations " +
                        w +
                        "! You won the game!"
        );

        JDialog d =
                new JDialog(
                        this,
                        "🏆 CHAMPION 🏆",
                        true
                );

        d.setSize(700,620);
        d.setUndecorated(true);

        JPanel p = new JPanel();

        p.setLayout(
                new BoxLayout(
                        p,
                        BoxLayout.Y_AXIS
                )
        );

        p.setBackground(
                new Color(
                        245,
                        248,
                        255
                )
        );

        p.setBorder(
                BorderFactory.createLineBorder(
                        PINK,
                        5
                )
        );

        p.add(
                label(
                        "🎉 🎆 🎊 🎆 🎉",
                        38,
                        PINK
                )
        );

        p.add(
                label(
                        "🏆",
                        90,
                        GOLD
                )
        );

        p.add(
                label(
                        "🎉 CONGRATULATIONS! 🎉",
                        38,
                        PINK
                )
        );

        p.add(
                label(
                        "👑 " +
                                w.toUpperCase() +
                                " 👑",
                        38,
                        CYAN
                )
        );

        p.add(
                label(
                        "🏆 YOU WON THE GAME! 🏆",
                        27,
                        GREEN
                )
        );

        p.add(
                label(
                        "🔥 CHAMPION 🔥",
                        25,
                        PURPLE
                )
        );

        p.add(
                label(
                        "😊 " +
                                l +
                                " — BETTER LUCK NEXT TIME! 💪",
                        16,
                        PINK
                )
        );

        JPanel f =
                new JPanel(
                        new GridLayout(
                                2,
                                6
                        )
                );

        f.setBackground(
                new Color(
                        245,
                        248,
                        255
                )
        );

        String[] em = {

                "🎆","🎉","🎊",
                "🥳","💥","⭐",
                "🌟","🎇","💫",
                "🏆","👑","✨"
        };

        JLabel[] fs =
                new JLabel[12];

        for(int i=0;i<12;i++) {

            fs[i] =
                    label(
                            em[i],
                            30,
                            GOLD
                    );

            f.add(fs[i]);
        }

        p.add(f);

        p.add(
                Box.createVerticalStrut(10)
        );

        JButton again =
                button(
                        "🔄 PLAY AGAIN 🎮",
                        18
                );

        again.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        p.add(again);

        d.setContentPane(p);

        Point q =
                getLocationOnScreen();

        int fx =
                q.x +
                        (getWidth()
                                - d.getWidth())
                                / 2;

        int fy =
                q.y +
                        (getHeight()
                                - d.getHeight())
                                / 2;

        d.setLocation(
                fx,
                q.y - d.getHeight()
        );

        int[] y = {
                q.y - d.getHeight()
        };

        Timer[] slide = {
                null
        };

        slide[0] =
                new Timer(
                        12,
                        e -> {

                            y[0] += 30;

                            if(y[0] >= fy) {

                                y[0] = fy;

                                slide[0].stop();
                            }

                            d.setLocation(
                                    fx,
                                    y[0]
                            );
                        }
                );

        Random rnd =
                new Random();

        Timer burst =
                new Timer(
                        130,
                        e -> {

                            for(JLabel z : fs)

                                z.setVisible(
                                        rnd.nextBoolean()
                                );
                        }
                );

        slide[0].start();
        burst.start();

        again.addActionListener(e -> {

            slide[0].stop();
            burst.stop();

            d.dispose();

            clear();

            firstPlayer();
        });

        d.setVisible(true);
    }

    // ================= DRAW POPUP =================

    void drawPopup() {

        speak(
                "It's a draw. Amazing game!"
        );

        JOptionPane.showMessageDialog(
                this,
                "🤝🎮🤝\n\n" +
                        "IT'S A DRAW!\n\n" +
                        "🔥 AMAZING GAME! 🔥",
                "🤝 DRAW",
                JOptionPane.INFORMATION_MESSAGE
        );

        clear();

        firstPlayer();
    }

    // ================= CLEAR =================

    void clear() {

        for(JButton[] row : cells)

            for(JButton b : row) {

                b.setText("✨");
                b.setBackground(CELL);
                b.setForeground(WHITE);
            }

        gameOver = false;
    }

    // ================= UPDATE =================

    void update() {

        if(turnLabel == null)
            return;

        turnLabel.setText(
                "🎯 " +
                        currentPlayer +
                        " — YOUR TURN " +
                        (
                                currentSymbol.equals("X")
                                        ? "❌"
                                        : "⭕"
                        )
        );

        scoreLabel.setText(
                "🏆 " +
                        player1 +
                        " : " +
                        score1 +
                        "    ⚔️ VS ⚔️    " +
                        player2 +
                        " : " +
                        score2
        );
    }

    // ================= UPDATE DATABASE SCORE =================

    void updateDatabaseScore(
            int playerId
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();

            if(con == null)
                return;

            String sql =
                    "UPDATE players " +
                            "SET score = score + 1 " +
                            "WHERE player_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    playerId
            );

            ps.executeUpdate();

            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // ================= RESET SCORE =================

    void resetDatabaseScore() {

        try {

            Connection con =
                    DBConnection.getConnection();

            if(con == null)
                return;

            String sql =
                    "UPDATE players " +
                            "SET score = 0 " +
                            "WHERE player_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    player1Id
            );

            ps.executeUpdate();

            ps.setInt(
                    1,
                    player2Id
            );

            ps.executeUpdate();

            ps.close();
            con.close();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to reset score."
            );
        }
    }

    // ================= SAVE GAME =================

    void saveGame(
            Integer winnerId,
            String status
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();

            if(con == null)
                return;

            String sql =
                    "INSERT INTO games " +
                            "(player1_id, player2_id, " +
                            "winner_id, game_status) " +
                            "VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(
                    1,
                    player1Id
            );

            ps.setInt(
                    2,
                    player2Id
            );

            if(winnerId == null) {

                ps.setNull(
                        3,
                        Types.INTEGER
                );

            } else {

                ps.setInt(
                        3,
                        winnerId
                );
            }

            ps.setString(
                    4,
                    status
            );

            ps.executeUpdate();

            ps.close();
            con.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // ================= GAME HISTORY =================

    void showHistory() {

        StringBuilder history =
                new StringBuilder();

        String sql =
                "SELECT g.game_id, " +
                        "p1.player_name AS player1, " +
                        "p2.player_name AS player2, " +
                        "w.player_name AS winner, " +
                        "g.game_status, " +
                        "g.played_at " +
                        "FROM games g " +
                        "JOIN players p1 " +
                        "ON g.player1_id = p1.player_id " +
                        "JOIN players p2 " +
                        "ON g.player2_id = p2.player_id " +
                        "LEFT JOIN players w " +
                        "ON g.winner_id = w.player_id " +
                        "ORDER BY g.game_id DESC";

        try {

            Connection con =
                    DBConnection.getConnection();

            if(con == null)
                return;

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            history.append(
                    "📜 GAME HISTORY\n\n"
            );

            while(rs.next()) {

                history.append(
                        "Game ID: "
                );

                history.append(
                        rs.getInt(
                                "game_id"
                        )
                );

                history.append("\n");

                history.append(
                        "Player 1: "
                );

                history.append(
                        rs.getString(
                                "player1"
                        )
                );

                history.append("\n");

                history.append(
                        "Player 2: "
                );

                history.append(
                        rs.getString(
                                "player2"
                        )
                );

                history.append("\n");

                String winner =
                        rs.getString(
                                "winner"
                        );

                history.append(
                        "Winner: "
                );

                history.append(
                        winner == null
                                ? "DRAW"
                                : winner
                );

                history.append("\n");

                history.append(
                        "Status: "
                );

                history.append(
                        rs.getString(
                                "game_status"
                        )
                );

                history.append("\n");

                history.append(
                        "Date: "
                );

                history.append(
                        rs.getTimestamp(
                                "played_at"
                        )
                );

                history.append("\n");

                history.append(
                        "------------------------\n"
                );
            }

            rs.close();
            st.close();
            con.close();

            JTextArea area =
                    new JTextArea(
                            history.toString()
                    );

            area.setEditable(false);

            area.setFont(
                    new Font(
                            "Monospaced",
                            Font.PLAIN,
                            14
                    )
            );

            JScrollPane scroll =
                    new JScrollPane(area);

            scroll.setPreferredSize(
                    new Dimension(
                            600,
                            450
                    )
            );

            JOptionPane.showMessageDialog(
                    this,
                    scroll,
                    "📜 GAME HISTORY",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error!\n" +
                            e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= VOICE =================

    void speak(String text) {

        new Thread(() -> {

            try {

                String s =
                        text.replace(
                                "'",
                                "''"
                        );

                String cmd =
                        "$v=New-Object -ComObject SAPI.SpVoice;" +
                                "$v.Volume=100;" +
                                "$v.Rate=2;" +
                                "$v.Speak('" +
                                s +
                                "');";

                new ProcessBuilder(
                        "powershell.exe",
                        "-NoProfile",
                        "-Command",
                        cmd
                ).start();

            } catch(Exception e) {

            }

        }).start();
    }

    // ================= MAIN =================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () ->
                        new TicTacToe()
                                .setVisible(true)
        );
    }
}