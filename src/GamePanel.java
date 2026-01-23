import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel {

    // Deklarasi untuk permainan
    private final int NUM_IMAGES = 13; // Jumlah gambar
    private final int CELL_SIZE = 32; // Ukuran sel
    private final int COVER_FOR_CELL = 10; // Penutup untuk sel
    private final int MARK_FOR_CELL = 10; // Penanda untuk sel
    private final int EMPTY_CELL = 0; // Sel kosong
    private final int MINE_CELL = 9; // Sel dengan ranjau
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL; // Sel tertutup yang berisi ranjau
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL; // Sel yang ditandai dan berisi ranjau
    private final int DRAW_MINE = 9; // Gambar ranjau
    private final int DRAW_COVER = 10; // Gambar penutup
    private final int DRAW_MARK = 11; // Gambar penanda
    private final int DRAW_WRONG_MARK = 12; // Gambar penanda salah
    private final int N_MINES = 40; // Jumlah ranjau
    private final int N_ROWS = 16; // Jumlah baris
    private final int N_COLS = 16; // Jumlah kolom
    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1; // Lebar papan
    private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1; // Tinggi papan
    private int[] field; // Array untuk menyimpan keadaan setiap sel
    private boolean inGame; // Menyatakan apakah permainan sedang berjalan
    private int minesLeft; // Jumlah ranjau yang tersisa
    private Image[] img; // Array untuk menyimpan gambar
    private int allCells; // Jumlah seluruh sel
    private final JLabel status; // Label untuk menampilkan status permainan
    private boolean firstClick = true; // Menyatakan apakah ini klik pertama
    private JButton playAgainButton; // Tombol untuk bermain lagi

    // Getter dan setter untuk firstClick
    public boolean isFirstClick() {
        return firstClick;
    }

    public void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }

    // Konstruktor GamePanel yang menerima JLabel untuk status
    private final Font statusFont = new Font("Arial", Font.BOLD, 18);

    public GamePanel(JLabel status) {
        this.status = status;
        // Atur font ke label status
        this.status.setFont(statusFont);
        initBoard(); // Inisialisasi papan permainan
    }

    // Inisialisasi papan permainan
    private void initBoard() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT)); // Set ukuran preferensi panel
        img = new Image[NUM_IMAGES]; // Inisialisasi array gambar

        // Memuat gambar ke dalam array
        for (int i = 0; i < NUM_IMAGES; i++) {
            var path = "src/Mineimg/" + i + ".png"; // Path gambar
            img[i] = (new ImageIcon(path)).getImage(); // Muat gambar
        }

        // Inisialisasi tombol "Play Again"
        playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds((BOARD_WIDTH / 2) - 50, (BOARD_HEIGHT / 2) - 15, 100, 30); // Set posisi dan ukuran tombol
        playAgainButton.setVisible(false); // Sembunyikan tombol awalnya
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(); // Mulai permainan baru jika tombol diklik
            }
        });
        setLayout(null); // Set layout null
        add(playAgainButton); // Tambahkan tombol ke panel

        addMouseListener(new MinesAdapter(this)); // Tambahkan MouseListener untuk deteksi klik mouse
        newGame(); // Mulai permainan baru
    }

    // Metode untuk memulai permainan baru
    public void newGame() {
        int cell;

        firstClick = true; // Set firstClick ke true
        var random = new Random(); // Inisialisasi objek Random
        inGame = true; // Set inGame ke true
        minesLeft = N_MINES; // Set jumlah ranjau yang tersisa
        allCells = N_ROWS * N_COLS; // Hitung jumlah semua sel
        field = new int[allCells]; // Inisialisasi array field

        // Tutupi semua sel dengan COVER_FOR_CELL
        for (int i = 0; i < allCells; i++) {
            field[i] = COVER_FOR_CELL;
        }

        status.setText(Integer.toString(minesLeft)); // Set teks status dengan jumlah ranjau yang tersisa

        int i = 0;

        // Tempatkan ranjau secara acak di papan permainan
        while (i < N_MINES) {
            int position = (int) (allCells * random.nextDouble()); // Tentukan posisi acak

            // Pastikan posisi valid dan belum ada ranjau di sana
            if ((position < allCells) && (field[position] != COVERED_MINE_CELL)) {
                int current_col = position % N_COLS; // Tentukan kolom saat ini
                field[position] = COVERED_MINE_CELL; // Set sel dengan ranjau tertutup
                i++;

                // Perbarui nilai sel di sekitar ranjau
                if (current_col > 0) {
                    cell = position - 1 - N_COLS;
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

                    cell = position + N_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_COLS;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + N_COLS + 1;
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

        repaint(); // Repaint panel untuk menggambar ulang papan
        playAgainButton.setVisible(false); // Sembunyikan tombol Play Again
    }

    // Metode untuk menemukan sel kosong di sekitar sel yang dipilih
    public void find_empty_cells(int j) {
        int current_col = j % N_COLS;
        int cell;

        if (current_col > 0) {
            cell = j - N_COLS - 1;
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

            cell = j + N_COLS - 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - N_COLS;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;

                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + N_COLS;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;

                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (N_COLS - 1)) {
            cell = j - N_COLS + 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;

                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + N_COLS + 1;
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

    // Metode untuk menggambar komponen pada panel
    public void paintComponent(Graphics g) {
        int uncover = 0;

        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {

                int cell = field[(i * N_COLS) + j];

                if (inGame && cell == MINE_CELL) {
                    inGame = false;
                }

                if (!inGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
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

    // Metode untuk memperbarui status permainan
    public void updateStatus(String message) {
        status.setText(message);
    }

    // Getter untuk array field
    public int[] getField() {
        return field;
    }

    // Getter untuk ukuran sel
    public int getCellSize() {
        return CELL_SIZE;
    }

    // Getter untuk jumlah kolom
    public int getNCols() {
        return N_COLS;
    }

    // Getter untuk jumlah baris
    public int getNRows() {
        return N_ROWS;
    }

    // Getter untuk status permainan
    public boolean isInGame() {
        return inGame;
    }

    // Setter untuk status permainan
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    // Getter untuk jumlah ranjau yang tersisa
    public int getMinesLeft() {
        return minesLeft;
    }

    // Setter untuk jumlah ranjau yang tersisa
    public void setMinesLeft(int minesLeft) {
        this.minesLeft = minesLeft;
    }

    // Getter untuk nilai COVER_FOR_CELL
    public int getCoverForCell() {
        return COVER_FOR_CELL;
    }

    // Getter untuk nilai MARK_FOR_CELL
    public int getMarkForCell() {
        return MARK_FOR_CELL;
    }

    // Getter untuk nilai MINE_CELL
    public int getMineCell() {
        return MINE_CELL;
    }

    // Getter untuk nilai COVERED_MINE_CELL
    public int getCoveredMineCell() {
        return COVERED_MINE_CELL;
    }

    // Getter untuk nilai MARKED_MINE_CELL
    public int getMarkedMineCell() {
        return MARKED_MINE_CELL;
    }

    // Getter untuk nilai EMPTY_CELL
    public int getEmptyCell() {
        return EMPTY_CELL;
    }
}
