package tic_tac_toe;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class MainWindow extends BaseWindow {
    
    private List<Window> gameWindows = new ArrayList<>();
    
    /**
     * Választhatunk az adott ablak méretek közül és a program hozzárendel
     * mindhez egy megfelelő eseménykezelőt
     */
    public MainWindow() {
        
        JButton small = new JButton();
        small.setText("6 x 6");
        
        small.addActionListener(getActionListener(6));
        
        JButton medium = new JButton();
        medium.setText("10 x 10");

        medium.addActionListener(getActionListener(10));

        JButton large = new JButton();
        large.setText("14 x 14");
        
        large.addActionListener(getActionListener(14));
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(small);
        getContentPane().add(medium);
        getContentPane().add(large);
    }
     /**
     * Egy ablakméret kiválasztásakor aktiválodik az esemény és létrejön az új ablak
     * Ezt az új ablakot a program hozzáadja az ablakok listájához és láthatóva teszi azt
     */
    private ActionListener getActionListener(final int size) {
        return new ActionListener() { 

            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = new Window(size, MainWindow.this);
                window.setVisible(true);
                gameWindows.add(window);
            }
            
        };
    }
    
    /**
     *
     * Visszaadja a meglévő ablakok listáját
     */
    public List<Window> getWindowList() {
        return gameWindows;
    }
    
    /**
     * Kilépést felülíró metódus
     */
    @Override
    protected void doUponExit() {
        System.exit(0);
    }

}
