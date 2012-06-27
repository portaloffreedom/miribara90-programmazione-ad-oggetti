/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

/**
 *
 * @author miriam
 */
abstract public class Casella {
    
    abstract public String getFormula();
    
    abstract public String getRisultato();
    
    static public Casella NewCasella(String testo){
        return new CasellaTesto(testo);
    }

}
