import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Minesweeper extends JFrame {

    private JLabel status;
    private GamePanel gamePanel;

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        status = new JLabel("");
        add(status, BorderLayout.SOUTH);

        gamePanel = new GamePanel(status);
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
