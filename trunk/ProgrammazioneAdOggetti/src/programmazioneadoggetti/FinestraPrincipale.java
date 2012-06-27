/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.TextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author miriam
 */
public class FinestraPrincipale extends JFrame {
    
    private JButton ok;
    private JButton cerca;
    private TextField campoTesto;
    private Tabella tabella;

    public FinestraPrincipale() throws HeadlessException {
        super("Finestra Principale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        JPanel pannelloRicerca = new JPanel();
        pannelloRicerca.setLayout(new BoxLayout(pannelloRicerca, BoxLayout.X_AXIS));
        
        ok = new JButton("ok");
        cerca = new JButton("cerca");
        campoTesto = new TextField();
        
        pannelloRicerca.add(campoTesto);
        pannelloRicerca.add(ok);
        pannelloRicerca.add(cerca);
        
        
        
        JPanel tabellaPanel = new JPanel();
        tabella = new Tabella();
        tabellaPanel.add(tabella); 
        
        
        Container contentPane = this.getContentPane();
        contentPane.add(pannelloRicerca, BorderLayout.NORTH);
        contentPane.add(tabellaPanel,BorderLayout.CENTER);
        
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    
    
    
}
