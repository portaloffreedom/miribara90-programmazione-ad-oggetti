/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

/**
 *
 * @author miriam
 */
public class CasellaTesto extends Casella {
    
    private String testo;

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
