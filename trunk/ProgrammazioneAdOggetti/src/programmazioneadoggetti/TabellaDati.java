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
    
    public TabellaDati(JTable jtable, int rows, int columns) {
        this.jtable = jtable;
        this.memoria = new Casella[rows][columns];
        this.columnNames = new String[columns];
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
    
}
