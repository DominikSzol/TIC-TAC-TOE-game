package tic_tac_toe;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends BaseWindow {

    private int size;
    private Model model;
    private JLabel label;
    private MainWindow mainWindow;
    private JButton[][] playfield;
    
    /**
     * 
     * @param size
     * Az adott ablak mérete pl. 6x6
     * @param mainWindow
     * A fő ablak ami alá tartozik
     * Létrejövő játékablak adott méretben legenerálja a pályát
     * Beállítja a különféle feliratokat és gombokat
     */
    public Window(int size, MainWindow mainWindow) {
        this.size = size;
        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);
        model = new Model(size);
        playfield = new JButton[size][size];

        JPanel top = new JPanel();
        
        label = new JLabel();
        updateLabelText();
        
        JButton newGameButton = new JButton();
        newGameButton.setText("Új játék");
        newGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
            
        });
        
        top.add(label);
        top.add(newGameButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                JButton button = new JButton();
                playfield[i][j] = button;
                mainPanel.add(playfield[i][j]);
                addButton(button, i,j);
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    /**
     * 
     * @param button
     * Az aktuálisan vizsgálandó gomb amire rákattintottunk
     * @param i
     * A gomb sorindexe
     * @param j
     * A gomb oszlopindexe
     * Frissíti az aktuális játékost
     * Megnézi hogy van-e a játéknak nyertese az aktuális lépés után
     * Megnézi hogy kell-e törölni mezőt az aktuális lépés után
     */
    private void addButton(JButton button, final int i, final int j) {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player newValue = model.step(i, j);
                model.dopingAPlayer(i, j);
                button.setText(newValue.name());
                updateLabelText();
                
                Player winner = model.findWinner();
                if(winner == Player.DRAW) {
                    showGameOverMessage(winner);
                } else if (winner != Player.NOBODY) {
                    showGameOverMessage(winner);
                }
                //model.dopingAPlayer();
                if(model.getDelete2()) {
                    selectARandomField().setText("");
                    selectARandomField().setText("");
                } else if(model.getDelete1()) {
                    selectARandomField().setText("");
                }
                
            }
        });
    }
    /**
     * 
     * @param winner
     * A győztes játékos akit paraméterül kapott
     * Az adott játékos lehet győztes, de jelentheti azt is hogy a játék döntetlennel ért véget
     * Ezt külön vizsgálja a metódus
     */
    private void showGameOverMessage(Player winner) {
        if(winner == Player.DRAW) {
            JOptionPane.showMessageDialog(this, "A játék döntetlennel ért véget");
            newGame();
        } else {
            JOptionPane.showMessageDialog(this, "Játék vége. Nyert: " + winner.name());
            newGame();
        }
    }
    /**
     * Új játék kezdése 
     * Automatikusan meghívódik
     * Újragenerálja a pályát a régit pedig eldobja
     */
    private void newGame() {
        Window newWindow = new Window(size, mainWindow);
        newWindow.setVisible(true);
        
        this.dispose();
        mainWindow.getWindowList().remove(this);
    }
    /**
     * Frissíti az aktuális játékost jelző mezőt
     */
    private void updateLabelText() {
        label.setText("Aktuális játékos: "
                + model.getActualPlayer().name());
    }
    /**
     * Kiválaszt egy olyan random mezőt ami az éppen most aktuális játékhoz tartozik
     * Aki most lépett
     * És visszaadja azt
     */
    private JButton selectARandomField() {
        Random r = new Random();
        int i = r.nextInt(size);
        int j = r.nextInt(size);
        String s = model.getActualPlayer().name();
        if(s == "X") {
            s = "O";
        } else {
            s = "X";
        }
        while(playfield[i][j].getText() != s) {
            i = r.nextInt(size);
            j = r.nextInt(size);
        }
        model.freeField(i, j);
        return playfield[i][j];
    }

    /**
     * Felülírja a kilépés metódusát
     * Kilépés során törli az adott ablakot
     */
    @Override
    protected void doUponExit() {
        super.doUponExit();
        mainWindow.getWindowList().remove(this);
    }
    
}
