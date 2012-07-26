/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.io.Serializable;

/**
 * Implementa una casella generica
 * @author miriam
 */
abstract public class Casella implements Serializable {
    
    /**
     * ritorna la stringa della formula(se esistente)
     * @return
     */
    abstract public String getFormula();
    
    /**
     * ritorna la presentazione standard della casella
     * @return 
     */
    abstract public String getRisultato();
    
    /**
     * crea una nuova casella
     * @param testo parametro in ingresso
     * @param tabella parametro in ingresso
     * @return casella(testo o formula)
     */
    static public Casella NewCasella(String testo, TabellaDati tabella){
        try {
            Casella cas = new CasellaFormula(testo,tabella);
            System.out.println("Convertita in casella formula");
            return cas;
        } catch (ConversioneNonRiuscitaException ex) {
            System.out.println("Convertita in casella di testo");
            return new CasellaTesto(testo);
        }
    }

}
