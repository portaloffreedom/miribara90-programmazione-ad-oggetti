/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author matteo
 */
public class Ricerca implements ActionListener{
    Tabella tabellagrafica;
    TabellaDati tabella;
    TextField campoTesto;

    public Ricerca(Tabella tabellagrafica, TabellaDati tabella, TextField campoTesto) {
        this.tabellagrafica = tabellagrafica;
        this.tabella = tabella;
        this.campoTesto = campoTesto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Coordinate coordinate = this.tabella.cerca(campoTesto.getText());
            int x = coordinate.getX();
            int y = coordinate.getY();
            
            System.out.println("casella trovata a  x="+x+" y="+y);
            //TODO visualizzazione grafica del risulatato
            tabellagrafica.changeSelection(y, x, false, false);
            
        } catch (PatternNotFoundException ex) {
            System.out.println(ex);
            //TODO visualizzazione grafica di non avere trovato la cosa cercata
            
        }
    }
    
    
}
