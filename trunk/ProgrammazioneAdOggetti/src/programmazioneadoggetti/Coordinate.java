/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

/**
 * Classe delle coordinate
 * @author Miriam
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * costruttore 
     * @param x ascissa
     * @param y ordinata
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * ascissa
     * @return ascissa 
     */
    public int getX() {
        return x;
    }

    /**
     * ordinata
     * @return ordinata
     */
    public int getY() {
        return y;
    }
}
