import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesAdapter extends MouseAdapter {

    private GamePanel gamePanel;

    public MinesAdapter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gamePanel.isInGame()) {
            return;
        }

        int x = e.getX();
        int y = e.getY();
        int cCol = x / gamePanel.getCellSize();
        int cRow = y / gamePanel.getCellSize();
        boolean doRepaint = false;

        if ((x < gamePanel.getNCols() * gamePanel.getCellSize()) && (y < gamePanel.getNRows() * gamePanel.getCellSize())) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] > gamePanel.getMineCell()) {
                    doRepaint = true;

                    if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] <= gamePanel.getCoveredMineCell()) {
                        if (gamePanel.getMinesLeft() > 0) {
                            gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] += gamePanel.getMarkForCell();
                            gamePanel.setMinesLeft(gamePanel.getMinesLeft() - 1);
                            String msg = Integer.toString(gamePanel.getMinesLeft());
                            gamePanel.updateStatus(msg);
                        } else {
                            gamePanel.updateStatus("No marks left");
                        }
                    } else {
                        gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] -= gamePanel.getMarkForCell();
                        gamePanel.setMinesLeft(gamePanel.getMinesLeft() + 1);
                        String msg = Integer.toString(gamePanel.getMinesLeft());
                        gamePanel.updateStatus(msg);
                    }
                }
            } else {
                System.out.println(gamePanel.isFirstClick());
                if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] > gamePanel.getCoveredMineCell()) {
                    return;
                }

                if ((gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] > gamePanel.getMineCell()) &&
                        (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] < gamePanel.getMarkedMineCell())) {
                    gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] -= gamePanel.getCoverForCell();
                    doRepaint = true;

                    if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] == gamePanel.getMineCell()) {
                        gamePanel.setInGame(false);
                        if (gamePanel.isFirstClick()) {
                            gamePanel.newGame();
                            gamePanel.repaint();
                            gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] -= gamePanel.getCoverForCell();
                            doRepaint = true;
                        }
                    }

                    if (gamePanel.getField()[(cRow * gamePanel.getNCols()) + cCol] == gamePanel.getEmptyCell()) {
                        gamePanel.find_empty_cells((cRow * gamePanel.getNCols()) + cCol);
                    }
                    if (gamePanel.isFirstClick()) {
                        gamePanel.setFirstClick(false);
                    }
                }
            }

            if (doRepaint) {
                gamePanel.repaint();
            }
        }
    }
}
