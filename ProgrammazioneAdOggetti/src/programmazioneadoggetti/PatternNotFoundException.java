/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

/**
 *
 * @author matteo
 */
public class PatternNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>PatternNotFoundException</code> without detail message.
     */
    public PatternNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>PatternNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PatternNotFoundException(String msg) {
        super(msg);
    }
}
