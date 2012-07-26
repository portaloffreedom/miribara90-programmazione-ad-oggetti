/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Classe che memorizza i dati della tabella
 * @author miriam
 */
public class TabellaDati implements TableModel, Serializable {
    private String[] columnNames;
    private Casella[][] memoria;
    transient private JTable jtable;
    
    private int columns;
    private int rows;
    /**
     * Costruttore
     * @param jtable tabella grafica su cui operare
     * @param rows numero di righe
     * @param columns numero di colonne
     */
    public TabellaDati(JTable jtable, int rows, int columns) {
        this.jtable = jtable;
        this.memoria = new Casella[rows][columns];
        this.columnNames = new String[columns];
        this.lastResearchPattern = "";
        this.columns = columns;
        this.rows = rows;
        char carattere = 'A'-1;
        
        /*
        for (int i=0; i< columns; i++) {
            for (int j=0; j< rows; j++) {
                //this.memoria[j][i] = new CasellaTesto(i+":"+j);
                if (i == 0)
                    this.memoria[j][i] = new CasellaTesto(""+j);
            }
            
            this.columnNames[i] = ""+carattere;
            carattere++;
        }
        */
        
        for (int j=0; j<rows; j++)
            this.memoria[j][0] = new CasellaTesto(""+j);
        
        for (int i=0; i<columns; i++){
            this.columnNames[i] = ""+carattere;
            carattere++;
        }
        
        this.columnNames[0] = "♥";
    }

    /**
     * Ritorna la matrice che memorizza le caselle
     * @return 
     */
    public Casella[][] getMemoria() {
        return memoria;
    }

    /**
     * Imposta una nuova tabella
     * @param memoria 
     */
    public void setMemoria(Casella[][] memoria) {
        this.memoria = memoria;
        jtable.tableChanged(null);
    }

    /**
     * Ricollega la tabella alla grafica
     * @param jtable 
     */
    public void setJtable(JTable jtable) {
        this.jtable = jtable;
    }

    @Override
    public int getRowCount() {
        return memoria.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getColumnName(columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex <= 0)
            return false;
        else
            return true;
    }

    /**
     * ritorna il riferimento alla cella 
     * @param rowIndex riga dell'elelemento
     * @param columnIndex colonna dell'elemento
     * @return il riferimento alla cella
     */
    public Casella getRoughValueAt(int rowIndex, int columnIndex) {
        return memoria[rowIndex][columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Casella casella =memoria[rowIndex][columnIndex];
        
        
        if (casella == null)
            return null;
        else {
            int x = jtable.getSelectedRow(); //QUANDO PASSO SOPRA ALLA CASELLA 
            int y = jtable.getSelectedColumn(); //VEDO SE IL RIS ERA UNA FORMULA
            if (x == rowIndex && y == columnIndex )
                return casella.getFormula();
            return casella.getRisultato();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        memoria[rowIndex][columnIndex] = Casella.NewCasella((String)aValue, this);
        jtable.tableChanged(null);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }
    
    private Coordinate lastResearchPos;
    private String     lastResearchPattern;
    /**
     * Ricerca delle coordinate
     * @param pattern elemento
     * @return coordinate dell'elemento
     * @throws PatternNotFoundException 
     */
    public Coordinate cerca(String pattern) throws PatternNotFoundException {
        
        pattern = ".*"+pattern+".*";
        
        
        if (!lastResearchPattern.equals(pattern)) {
            lastResearchPos = new Coordinate(columns-1, rows-1);
            lastResearchPattern = pattern;
        }
        
        int x = (lastResearchPos.getX()+1)%columns;
        int y = (lastResearchPos.getY());//%rows;
        
        
        
        Casella prova;
        for (; true; y = (y+1)%rows) {
            for (; x != 0; x = (x+1)%columns) {
                System.out.println("giro x=" + x + " y=" + y 
                        + " --- partenza da x=" + lastResearchPos.getX() 
                        + " y=" + lastResearchPos.getY());
                prova = memoria[y][x];
                if (prova != null) {
                    //if (prova.getFormula().equals(pattern) || prova.getRisultato().equals(pattern)) {
                    if (prova.getFormula().matches(pattern) || prova.getRisultato().matches(pattern)) {
                        lastResearchPos = new Coordinate(x, y);
                        return lastResearchPos;
                    }
                }

                if (x == lastResearchPos.getX() && y == lastResearchPos.getY()) {
                    throw new PatternNotFoundException("pattern \""+pattern+"\" non trovato");
                }
            }
            // Questa riga evita la ricerca nella prima colonna, quella 
            // contenente i numeri delle righe
            x++;
        }
    }
    
}
