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
            System.out.print("Salvataggio in corso");
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(fileName));
            System.out.print(".");
            file.writeObject(tabellaDati);
            System.out.print(".");
            file.flush();
            System.out.println(".");
            file.close();
            System.out.println("file salvato correttamenete");
            
        } catch (FileNotFoundException ex) {
            System.out.println("file non trovato!\n"+ex);
        } catch (IOException ex) {
            System.out.println("Errore nell'apertura del file!\n"+ex);
        }
    }
    
    public void carica (String fileName) {
        try {
            System.out.print("Caricamento in corso");
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(fileName));
            
            System.out.print(".");
            tabellaDati = (TabellaDati) file.readObject();
            System.out.print(".");
            this.setModel(tabellaDati);
            System.out.println(".");
            file.close();
            System.out.println("file caricato correttamenete");
            
        } catch (FileNotFoundException ex) {
            System.out.println("file non trovato!\n"+ex);
        } catch (IOException ex) {
            System.out.println("Errore nell'apertura del file!\n"+ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Errore nell'apertura del file!\n"+ex);
        }
        
        tabellaDati.setJtable(this);
        this.tableChanged(null);
    }
    
}
