import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame {

    private JLabel status;

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        status = new JLabel("");
        add(status, BorderLayout.SOUTH);

        var gamePanel = new GamePanel(status);
        add(gamePanel);

        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        var ex = new Minesweeper();
        ex.setVisible(true);
    }
}
