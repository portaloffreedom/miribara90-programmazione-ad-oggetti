/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author miriam
 */
public class FinestraPrincipale extends JFrame {
    
    private JButton cerca;
    private TextField campoTesto;
    private Tabella tabella;

    public FinestraPrincipale() throws HeadlessException {
        super("Finestra Principale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        JPanel pannelloRicerca = new JPanel();
        pannelloRicerca.setLayout(new BoxLayout(pannelloRicerca, BoxLayout.X_AXIS));
        
        JMenuBar menuBar = new JMenuBar();
        JMenuItem salva = new JMenuItem("salva");
        JMenuItem carica = new JMenuItem("carica");
        menuBar.add(carica);
        menuBar.add(salva);
        
        salva.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tabella.salva("datiTabella.dat");
            }
        });
        
        carica.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tabella.carica("datiTabella.dat");
            }
        });
        
        
        cerca = new JButton("cerca");
        campoTesto = new TextField();
        
        pannelloRicerca.add(campoTesto);
        pannelloRicerca.add(cerca);
        
        
        
        tabella = new Tabella();
        JScrollPane tabellaPanel = new JScrollPane(tabella);
        tabella.setFillsViewportHeight(true);
        
        
        Container contentPane = this.getContentPane();
        contentPane.add(pannelloRicerca, BorderLayout.SOUTH);
        contentPane.add(tabella.getTableHeader(), BorderLayout.PAGE_START);
        contentPane.add(tabella,BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        
        Ricerca ricercatore = new Ricerca(tabella, tabella.getTabellaDati(), campoTesto);
        cerca.addActionListener(ricercatore);
        campoTesto.addActionListener(ricercatore);

        
        this.pack();
        //this.setResizable(false);
        this.setVisible(true);
    }
    
    
    
}
