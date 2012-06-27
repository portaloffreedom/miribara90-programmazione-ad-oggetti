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

    public Tabella() {
        super(20, 10);
        this.tabellaDati = new TabellaDati(20, 10);
        this.setModel(tabellaDati);
    }
    
}
