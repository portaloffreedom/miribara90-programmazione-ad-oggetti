/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Classe di ricerca 
 * @author Miriam
 */
public class Ricerca implements ActionListener{
    Tabella tabellagrafica;
    TabellaDati tabella;
    TextField campoTesto;

    /**
     * costruttore
     * @param tabellagrafica rappresentazione grafica della tabella (jtable)
     * @param tabella tabella di memorizzazione dei dati
     * @param campoTesto campo da cui prendere il testo
     */
    public Ricerca(Tabella tabellagrafica, TabellaDati tabella, TextField campoTesto) {
        this.tabellagrafica = tabellagrafica;
        this.tabella = tabella;
        this.campoTesto = campoTesto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Coordinate coordinate = this.tabellagrafica.getTabellaDati().cerca(campoTesto.getText());
            int x = coordinate.getX();
            int y = coordinate.getY();
            
            System.out.println("casella trovata a  x="+x+" y="+y);
            tabellagrafica.changeSelection(y, x, false, false);
            
        } catch (PatternNotFoundException ex) {
            System.out.println(ex);
         
            JOptionPane.showMessageDialog(null,
            "La ricerca non ha prodotto risultati",
            "Attenzione",
            JOptionPane.WARNING_MESSAGE);
        }
    }
    
    
}
