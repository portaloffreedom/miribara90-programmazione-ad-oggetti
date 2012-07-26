/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.io.*;
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
        
        this.setColumnSelectionAllowed(true);
        this.setRowSelectionAllowed(true);
    }

    public TabellaDati getTabellaDati() {
        return tabellaDati;
    }
    
    public void salva (String fileName) {
        try {
            ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            
            file.writeObject(tabellaDati);
            file.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("file non trovato!\n"+ex);
        } catch (IOException ex) {
            System.out.println("Errore nell'apertura del file!\n"+ex);
        }
    }
    
    public void carica (String fileName) {
        try {
            ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            
            tabellaDati = (TabellaDati) file.readObject();
            file.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("file non trovato!\n"+ex);
        } catch (IOException ex) {
            System.out.println("Errore nell'apertura del file!\n"+ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Errore nell'apertura del file!\n"+ex);
        }
    }
    
}
