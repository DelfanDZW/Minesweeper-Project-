import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Minesweeper extends JFrame {

    private JLabel status;
    private JLabel timerLabel;
    private JButton resetBtn;
    private GamePanel gamePanel;

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        JPanel topPanel = new JPanel(new java.awt.BorderLayout());
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topPanel.setBackground(java.awt.Color.LIGHT_GRAY);

        status = new JLabel("000");
        status.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 24));
        status.setForeground(java.awt.Color.RED);
        status.setBackground(java.awt.Color.BLACK);
        status.setOpaque(true);
        status.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());

        timerLabel = new JLabel("000");
        timerLabel.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 24));
        timerLabel.setForeground(java.awt.Color.RED);
        timerLabel.setBackground(java.awt.Color.BLACK);
        timerLabel.setOpaque(true);
        timerLabel.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());

        resetBtn = new JButton("😊");
        resetBtn.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 20));
        resetBtn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        resetBtn.setFocusPainted(false);
        resetBtn.setPreferredSize(new java.awt.Dimension(40, 40));
        resetBtn.addActionListener(e -> {
            gamePanel.newGame();
            gamePanel.repaint();
        });

        JPanel centerBtnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        centerBtnPanel.setBackground(java.awt.Color.LIGHT_GRAY);
        centerBtnPanel.add(resetBtn);

        topPanel.add(status, java.awt.BorderLayout.WEST);
        topPanel.add(centerBtnPanel, java.awt.BorderLayout.CENTER);
        topPanel.add(timerLabel, java.awt.BorderLayout.EAST);

        add(topPanel, java.awt.BorderLayout.NORTH);

        gamePanel = new GamePanel(status, timerLabel, resetBtn);
        add(gamePanel);

        createMenuBar();

        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem easy = new JMenuItem("Easy (9\u00d79, 10 mines)");
        JMenuItem medium = new JMenuItem("Medium (16\u00d716, 40 mines)");
        JMenuItem hard = new JMenuItem("Hard (16\u00d730, 99 mines)");
        JMenuItem custom = new JMenuItem("Custom...");
        JMenuItem exit = new JMenuItem("Exit");

        easy.addActionListener(e -> {
            Difficulty d = Difficulty.EASY;
            gamePanel.setDifficulty(d.getRows(), d.getCols(), d.getMines());
            setTitle("Minesweeper - Easy");
        });

        medium.addActionListener(e -> {
            Difficulty d = Difficulty.MEDIUM;
            gamePanel.setDifficulty(d.getRows(), d.getCols(), d.getMines());
            setTitle("Minesweeper - Medium");
        });

        hard.addActionListener(e -> {
            Difficulty d = Difficulty.HARD;
            gamePanel.setDifficulty(d.getRows(), d.getCols(), d.getMines());
            setTitle("Minesweeper - Hard");
        });

        custom.addActionListener(e -> {
            CustomDialog dialog = new CustomDialog(this);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                gamePanel.setDifficulty(dialog.getRows(), dialog.getCols(), dialog.getMines());
                setTitle("Minesweeper - Custom (" + dialog.getRows() + "\u00d7" + dialog.getCols() + ")");
            }
        });

        exit.addActionListener(e -> System.exit(0));

        gameMenu.add(easy);
        gameMenu.add(medium);
        gameMenu.add(hard);
        gameMenu.add(custom);
        gameMenu.addSeparator();
        gameMenu.add(exit);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        Minesweeper ex = new Minesweeper();
        ex.setVisible(true);
    }
}
