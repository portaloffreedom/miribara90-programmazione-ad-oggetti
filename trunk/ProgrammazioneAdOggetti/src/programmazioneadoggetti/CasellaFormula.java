/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

/**
 *
 * @author miriam
 */
public class CasellaFormula extends Casella {
    private String formula;
    private int risultato;

    @Override
    public String getFormula() {
        return formula;
    }
    
    public int getRisutatoInt() {
        return risultato;
        //TODO calcolo della stringa
        //TODO throws impossibile convertire
    }

    @Override
    public String getRisultato() {
        return ""+getRisutatoInt();
    }
    
    
}
