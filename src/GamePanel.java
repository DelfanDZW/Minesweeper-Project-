import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel {

    private final int NUM_IMAGES = 12; 
    private final int CELL_SIZE = 32; 
    private final int COVER_FOR_CELL = 10; 
    private final int MARK_FOR_CELL = 10; 
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9; 
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL; 
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL; 
    private final int DRAW_MINE = 9; 
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11; 

    private int nMines = 40;
    private int nRows = 16; 
    private int nCols = 16; 
    private int boardWidth = nCols * CELL_SIZE + 1; 
    private int boardHeight = nRows * CELL_SIZE + 1; 
    private int[] field; 
    private boolean inGame; 
    private int minesLeft; 
    private Image[] img; 
    private int allCells; 
    private final JLabel status;
    private boolean firstClick = true;
    private JButton playAgainButton; 

    // Getter and setter for firstClick
    public boolean isFirstClick() {
        return firstClick;
    }

    public void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }

    private final Font statusFont = new Font("Arial", Font.BOLD, 18);

    public GamePanel(JLabel status) {
        this.status = status;
        this.status.setFont(statusFont);
        initBoard(); 
    }


    private void initBoard() {
        setPreferredSize(new Dimension(boardWidth, boardHeight)); 
        img = new Image[NUM_IMAGES];

        // load image to array array
        for (int i = 0; i < NUM_IMAGES; i++) {
            java.net.URL url = getClass().getResource("/Mineimg/" + i + ".png");
            if (url != null) {
                img[i] = (new ImageIcon(url)).getImage();
            } else {
                // Fallback to file path for development
                String path = "src/Mineimg/" + i + ".png"; 
                img[i] = (new ImageIcon(path)).getImage(); 
            }
        }

        playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds((boardWidth / 2) - 50, (boardHeight / 2) - 15, 100, 30); 
        playAgainButton.setVisible(false); 
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        setLayout(null); 
        add(playAgainButton); 

        addMouseListener(new MinesAdapter(this)); 
        newGame();
    }

    public void setDifficulty(int rows, int cols, int mines) {
        this.nRows = rows;
        this.nCols = cols;
        this.nMines = mines;
        this.boardWidth = nCols * CELL_SIZE + 1;
        this.boardHeight = nRows * CELL_SIZE + 1;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        newGame();

        // Notify the parent window to resize
        SwingUtilities.invokeLater(() -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.pack();
                window.setLocationRelativeTo(null);
            }
        });
    }

    public void newGame() {
        int cell;

        firstClick = true; 
        Random random = new Random(); 
        inGame = true; 
        minesLeft = nMines; 
        allCells = nRows * nCols; 
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {
            field[i] = COVER_FOR_CELL;
        }

        status.setText(Integer.toString(minesLeft)); 

        int i = 0;

        while (i < nMines) {
            int position = (int) (allCells * random.nextDouble()); 

            if ((position < allCells) && (field[position] != COVERED_MINE_CELL)) {
                int current_col = position % nCols;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - nCols;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + nCols - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - nCols;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + nCols;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (nCols - 1)) {
                    cell = position - nCols + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + nCols + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }

        // Reposition play again button for new board size
        playAgainButton.setBounds((boardWidth / 2) - 50, (boardHeight / 2) - 15, 100, 30);
        playAgainButton.setVisible(false); 

        repaint(); 
    }

    public void find_empty_cells(int j) {
        int current_col = j % nCols;
        int cell;

        if (current_col > 0) {
            cell = j - nCols - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + nCols - 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - nCols;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;

                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + nCols;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;

                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (nCols - 1)) {
            cell = j - nCols + 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + nCols + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        int uncover = 0;

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {

                int cell = field[(i * nCols) + j];

                if (inGame && cell == MINE_CELL) {
                    inGame = false;
                }

                if (!inGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }
                } else {
                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {
            inGame = false;
            status.setText("Game won");
            playAgainButton.setVisible(true);
        } else if (!inGame) {
            status.setText("Game lost");
            playAgainButton.setVisible(true);
        }
    }

    public void updateStatus(String message) {
        status.setText(message);
    }

    public int[] getField() {
        return field;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }

    public int getNCols() {
        return nCols;
    }

    public int getNRows() {
        return nRows;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getMinesLeft() {
        return minesLeft;
    }

    public void setMinesLeft(int minesLeft) {
        this.minesLeft = minesLeft;
    }

    public int getCoverForCell() {
        return COVER_FOR_CELL;
    }

    public int getMarkForCell() {
        return MARK_FOR_CELL;
    }

    public int getMineCell() {
        return MINE_CELL;
    }

    public int getCoveredMineCell() {
        return COVERED_MINE_CELL;
    }

    public int getMarkedMineCell() {
        return MARKED_MINE_CELL;
    }

    public int getEmptyCell() {
        return EMPTY_CELL;
    }
}
