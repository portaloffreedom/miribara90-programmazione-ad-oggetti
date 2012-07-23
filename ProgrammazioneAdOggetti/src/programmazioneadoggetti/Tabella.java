/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import javax.swing.JTable;

/**
 *
 * @author miriam
 */
public class Tabella extends JTable {
    private TabellaDati tabellaDati;
    static private int righe = 20;
    static private int colonne = 10;

    public Tabella() {
        super(righe, colonne);
        this.tabellaDati = new TabellaDati(this, righe, colonne);
        this.setModel(tabellaDati);
    }
    
}
