import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesAdapter extends MouseAdapter {

    // Variabel gamePanel untuk menyimpan referensi ke GamePanel
    private GamePanel gamePanel;

    // Konstruktor MinesAdapter menerima GamePanel sebagai parameter
    public MinesAdapter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Metode ini akan dipanggil ketika tombol mouse ditekan
    @Override
    public void mousePressed(MouseEvent e) {
        // Jika permainan tidak sedang berjalan, keluar dari metode
        if (!gamePanel.isInGame()) {
            return;
        }

        // Mendapatkan koordinat x dan y dari klik mouse
        int x = e.getX();
        int y = e.getY();
        // Menghitung kolom dan baris berdasarkan koordinat klik
        int cCol = x / gamePanel.getCellSize();
        int cRow = y / gamePanel.getCellSize();
        boolean doRepaint = false;

        // Memastikan klik berada dalam batas papan permainan
        if ((x < gamePanel.getNCols() * gamePanel.getCellSize()) && (y < gamePanel.getNRows() * gamePanel.getCellSize())) {
            // Jika tombol mouse kanan diklik
            if (e.getButton() == MouseEvent.BUTTON3) {
                // Jika sel yang diklik adalah sel yang tertutup
                if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] > gamePanel.getMineCell()) {
                    doRepaint = true;

                    // Jika sel adalah ranjau yang tertutup
                    if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] <= gamePanel.getCoveredMineCell()) {
                        // Jika masih ada ranjau yang tersisa untuk ditandai
                        if (gamePanel.getMinesLeft() > 0) {
                            // Tandai sel dengan ranjau
                            gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] += gamePanel.getMarkForCell();
                            gamePanel.setMinesLeft(gamePanel.getMinesLeft() - 1);
                            String msg = Integer.toString(gamePanel.getMinesLeft());
                            gamePanel.updateStatus(msg);
                        } else {
                            // Tidak ada tanda yang tersisa
                            gamePanel.updateStatus("No marks left");
                        }
                    } else {
                        // Jika sel sudah ditandai, hapus tanda tersebut
                        gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] -= gamePanel.getMarkForCell();
                        gamePanel.setMinesLeft(gamePanel.getMinesLeft() + 1);
                        String msg = Integer.toString(gamePanel.getMinesLeft());
                        gamePanel.updateStatus(msg);
                    }
                }
            } else {
                // Menampilkan nilai firstClick untuk debugging
                System.out.println(gamePanel.isFirstClick());
                // Jika sel adalah sel yang ditandai, keluar dari metode
                if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] > gamePanel.getCoveredMineCell()) {
                    return;
                }

                // Jika sel adalah sel yang tertutup
                if ((gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] > gamePanel.getMineCell()) &&
                        (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] < gamePanel.getMarkedMineCell())) {
                    // Buka sel tersebut
                    gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] -= gamePanel.getCoverForCell();
                    doRepaint = true;

                    // Jika sel adalah ranjau, akhiri permainan
                    if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] == gamePanel.getMineCell()) {
                        gamePanel.setInGame(false);
                        // Jika ini adalah klik pertama, mulai permainan baru
                        if (gamePanel.isFirstClick()) {
                            gamePanel.newGame();
                            gamePanel.repaint();
                            // Buka sel yang diklik setelah mulai permainan baru
                            gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] -= gamePanel.getCoverForCell();
                            doRepaint = true;
                        }
                    }

                    // Jika sel kosong, cari sel kosong di sekitarnya
                    if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] == gamePanel.getEmptyCell()) {
                        gamePanel.find_empty_cells((cRow * gamePanel.getNCols()) + cCol);
                    }
                    // Set firstClick ke false setelah klik pertama
                    if (gamePanel.isFirstClick()) {
                        gamePanel.setFirstClick(false);
                    }
                }
            }

            // Jika ada perubahan yang memerlukan penggambaran ulang, panggil repaint
            if (doRepaint) {
                gamePanel.repaint();
            }
        }
    }
}
