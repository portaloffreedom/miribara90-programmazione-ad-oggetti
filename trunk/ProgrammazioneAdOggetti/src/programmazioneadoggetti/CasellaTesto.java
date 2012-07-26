/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

/**
 * Estende la classe casella memorizzando il testo
 * @author miriam
 */
public class CasellaTesto extends Casella {
    
    private String testo;

    /**
     * costruttore
     * @param testo testo da memorizzare
     */
    public CasellaTesto(String testo) {
        this.testo = testo;
    }
    
    @Override
    public String getFormula() {
        return testo;
    }

    @Override
    public String getRisultato() {
        return testo;
    }
    
}
