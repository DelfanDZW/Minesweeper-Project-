/*Nama  : Delfan Zuffar Rajjaz Nuziar*/
/*NIM   : 235150707111010*/
/*Kelas : TI-A*/

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame {

    // Variabel status untuk menampilkan status permainan
    private JLabel status;

    // Konstruktor Minesweeper
    public Minesweeper() {
        initUI();
    }

    // Metode initUI untuk menginisialisasi antarmuka pengguna
    private void initUI() {
        // Membuat JLabel baru untuk status dan menambahkannya di bagian bawah (BorderLayout.SOUTH)
        status = new JLabel("");
        add(status, BorderLayout.SOUTH);

        // Membuat instance GamePanel dengan status sebagai parameter dan menambahkannya ke frame
        var gamePanel = new GamePanel(status);
        add(gamePanel);

        // Tidak mengizinkan ukuran frame untuk diubah
        setResizable(false);
        // Menyesuaikan ukuran frame berdasarkan komponen yang ditambahkan
        pack();

        // Mengatur judul frame menjadi "Minesweeper"
        setTitle("Minesweeper");
        // Menempatkan frame di tengah layar
        setLocationRelativeTo(null);
        // Mengatur operasi default ketika frame ditutup adalah keluar dari aplikasi
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Metode main sebagai titik masuk aplikasi
    public static void main(String[] args) {
        // Membuat instance Minesweeper dan mengatur visibilitasnya menjadi true
        var ex = new Minesweeper();
        ex.setVisible(true);
    }
}
