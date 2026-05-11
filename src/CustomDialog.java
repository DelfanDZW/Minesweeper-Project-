import javax.swing.*;
import java.awt.*;

public class CustomDialog extends JDialog {

    private JSpinner rowsSpinner;
    private JSpinner colsSpinner;
    private JSpinner minesSpinner;
    private boolean confirmed = false;

    public CustomDialog(JFrame parent) {
        super(parent, "Custom Difficulty", true);
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Rows
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Rows (5–30):"), gbc);
        gbc.gridx = 1;
        rowsSpinner = new JSpinner(new SpinnerNumberModel(16, 5, 30, 1));
        add(rowsSpinner, gbc);

        // Cols
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Columns (5–50):"), gbc);
        gbc.gridx = 1;
        colsSpinner = new JSpinner(new SpinnerNumberModel(16, 5, 50, 1));
        add(colsSpinner, gbc);

        // Mines
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Mines (1–max):"), gbc);
        gbc.gridx = 1;
        minesSpinner = new JSpinner(new SpinnerNumberModel(40, 1, 999, 1));
        add(minesSpinner, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> onOk());
        cancelButton.addActionListener(e -> onCancel());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());
    }

    private void onOk() {
        int rows = (int) rowsSpinner.getValue();
        int cols = (int) colsSpinner.getValue();
        int mines = (int) minesSpinner.getValue();
        int maxMines = (rows * cols) - 1;

        if (mines > maxMines) {
            JOptionPane.showMessageDialog(this,
                    "Too many mines! Maximum for " + rows + "×" + cols + " is " + maxMines + ".",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        confirmed = true;
        setVisible(false);
        dispose();
    }

    private void onCancel() {
        confirmed = false;
        setVisible(false);
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getRows() {
        return (int) rowsSpinner.getValue();
    }

    public int getCols() {
        return (int) colsSpinner.getValue();
    }

    public int getMines() {
        return (int) minesSpinner.getValue();
    }
}
