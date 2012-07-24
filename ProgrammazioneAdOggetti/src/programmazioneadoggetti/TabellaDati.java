/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author miriam
 */
public class TabellaDati implements TableModel {
    private String[] columnNames;
    private Casella[][] memoria;
    private JTable jtable;
    
    private int columns;
    private int rows;
    
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

    public Casella getRoughValueAt(int rowIndex, int columnIndex) {
        return memoria[rowIndex][columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Casella casella =memoria[rowIndex][columnIndex];
        
        
        if (casella == null)
            return null;
        else {
            int x = jtable.getSelectedRow();
            int y = jtable.getSelectedColumn();
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
    
    public Coordinate cerca(String pattern) throws PatternNotFoundException {
        
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
                    if (prova.getFormula().equals(pattern) || prova.getRisultato().equals(pattern)) {
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
