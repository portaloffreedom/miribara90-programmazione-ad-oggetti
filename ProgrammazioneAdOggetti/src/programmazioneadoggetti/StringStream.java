/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.io.IOException;

/**
 * Rappresentazione in stream di una stringa
 * @author miriam
 */
public class StringStream {
    private String stringa;
    private int pos;

    /**
     * costruttore
     * @param input stringa da trasformare 
     */
    public StringStream(String input) {
        stringa = input;
        pos = 0;
    }

    /**
     * Legge un carattere dallo stream
     * @return carattere
     * @throws IOException se si è arrivati alla fine della stringa
     */
    public char read() throws IOException {
        try {
            char letto = stringa.charAt(pos);
            pos++;
            return letto;
        } catch (IndexOutOfBoundsException ex) {
            throw new IOException("stream finito");
        }
    }
    
    /**
     * Torna indietro di una lettura
     * @throws IOException se c'è underflow
     */
    public void unreadChar() throws IOException {
        if (pos == 0)
            throw new IOException("Underflow dello stream");
        pos--;
    }
    
    /**
     * Fa il parsing di un intero dalla stringa
     * @return intero
     * @throws IOException se non si riesce a fare il parsing o se non si è arrivati alla fine dello stream
     */
    public int readInt() throws IOException {
        int intero = 0;
        
        char letto = this.read();
        if (letto < '0' || letto > '9')
            throw new IOException("Impossibile leggere un intero");
        
        try {
            while (letto >= '0' && letto <= '9') {
                intero = intero*10 + (letto-'0');
                letto = this.read();
            }
            this.unreadChar();
            
        } catch (IOException ex) {
        } finally {
            return intero;
        }
    }
    
}

