package tic_tac_toe;

/**
 * Egy játékos lehet X, O, NOBODY és ezek lesznek a különféle mezők értékei
 * Speciális érték a DRAW ilyenkor ha ezt kapja értékél a mező és adja vissza a findWinner() metódus
 * akkor a játék döntetlennel ért véget
 * @author pc
 */
public enum Player {
    X, O, NOBODY, DRAW
}
