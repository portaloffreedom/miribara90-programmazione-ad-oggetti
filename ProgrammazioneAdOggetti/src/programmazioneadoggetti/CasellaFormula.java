/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.io.IOException;

/**
 * Estende la classe casella implementando le formule
 * @author miriam
 */
public class CasellaFormula extends Casella {
    private String formula;
    private TabellaDati tabella;

    /**
     * costruttore 
     * @param formula formula da risolvere
     * @param tabella tabella in ingresso
     * @throws ConversioneNonRiuscitaException se la creazione non ha avuto successo
     */
    public CasellaFormula(String formula, TabellaDati tabella) throws ConversioneNonRiuscitaException {
        this.formula = formula;
        RisolviFormula(formula, tabella);
        this.tabella = tabella;
    }

    @Override
    public String getFormula() {
        return formula;
    }
    /**
     * Enumerato dei vari tipi di token
     */
    private enum Token {
        RIFERIMENTO,
        NUMERO,
        SOMMA,
        SOTTRAZIONE,
        MOLTIPLICAZIONE,
        DIVISIONE,
        APERTA_PARENTESI,
        CHIUSA_PARENTESI,
        FINE_STREAM;
        
        /**
         * valore numerico del token
         */
        public double valore;
        
        /**
         * Operazione di controllo 
         * @return vero se il token è un operatore
         */
        public boolean isOperator() {
            switch (this) {
                case SOMMA:
                case SOTTRAZIONE:
                case MOLTIPLICAZIONE:
                case DIVISIONE:
                    return true;
                default:
                    return false;
            }
        }
        
        /**
         * Operazione di controllo
         * @return vero se il token è un numero o un riferimento ad un numero
         */
        public boolean isNumber() {
            switch (this) {
                case NUMERO:
                case RIFERIMENTO:
                    return true;
                default:
                    return false;
            }
        }
    }
          
    /**
     * Converte una formula in un double(valore a virgola mobile)
     * @param formula la formula da convertire
     * @param tabella tabella in ingresso
     * @return il valore convertito
     * @throws ConversioneNonRiuscitaException se la stringa analizzata non è una formula
     */
    static public double RisolviFormula(String formula, TabellaDati tabella) throws ConversioneNonRiuscitaException {
        StringStream stream = new StringStream(formula);
        
        try {
            char primocarattere = stream.read();
            if (primocarattere != '=') {
                System.err.println("funzione non inizia con \"=\" ma con "+primocarattere+" unicode:"+Integer.toString(primocarattere));
                throw new ConversioneNonRiuscitaException();
        }
        } catch (IOException ex) {
            System.err.println("stream di caratteri non funzionante");
            throw new ConversioneNonRiuscitaException();
        }
        
        Token nullo = Token.APERTA_PARENTESI;
                
        return RisolviFormulaRic(stream, tabella, 0, nullo);
    }
    
    //static private double LASTVAL;
    /**
     * funzione interna che aiuta RisolviFormula
     * @param stream versione in stream della stringa da analizzare 
     * @param tabella tabella in ingresso
     * @param lastVal valore numerico letto precedentemente
     * @param lastToken token letto precedentemente
     * @return valore double dello stream rimasto
     * @throws ConversioneNonRiuscitaException se lo stream analizzato non rappresenta una formula
     */
    static private double RisolviFormulaRic(StringStream stream, TabellaDati tabella, double lastVal, Token lastToken) throws ConversioneNonRiuscitaException {
        
        Token token = RisolviToken(stream, tabella);
        switch (token) {
            case RIFERIMENTO:
            case NUMERO:
                if (!lastToken.isOperator()){
                    System.err.println("NUMERO: operatore precedente non operatore");
                    throw new ConversioneNonRiuscitaException();
                }
                return RisolviFormulaRic(stream, tabella, token.valore, token);
                
            case SOMMA:
                if (!lastToken.isNumber()) {
                    System.err.println("SOMMA: operatore precedente non numero");
                    throw new ConversioneNonRiuscitaException();
                }
                return lastVal + RisolviFormulaRic(stream, tabella, 0,token);
                
            case SOTTRAZIONE:
                if (!lastToken.isNumber()) {
                    System.err.println("SOTTRAZIONE: operatore precedente non numero");
                    throw new ConversioneNonRiuscitaException();
                }
                return lastVal - RisolviFormulaRic(stream, tabella, 0,token);
                
            case MOLTIPLICAZIONE:
                if (!lastToken.isNumber()) {
                    System.err.println("MOLTIPLICAZIONE: operatore precedente non numero");
                    throw new ConversioneNonRiuscitaException();
                }
                return lastVal * RisolviFormulaRic(stream, tabella, 0,token);
            
            case DIVISIONE:
                if (!lastToken.isNumber()) {
                    System.err.println("DIVISIONE: operatore precedente non numero");
                    throw new ConversioneNonRiuscitaException();
                }
                return lastVal / RisolviFormulaRic(stream, tabella, 0,token);
            
                
            case FINE_STREAM:
                if(!lastToken.isNumber()) {
                    System.err.println("FINE_STREAM: operatore precedente non numero");
                    throw new ConversioneNonRiuscitaException();
                }
                return lastVal;
            case CHIUSA_PARENTESI:
            default:
                System.err.println("TOKEN NON RICONOSCIUTO");
                throw new ConversioneNonRiuscitaException();
        }
      
    }
    /**
     * mangia un token dallo stream e lo ritorna
     * @param stream input
     * @param tabella tabella di riferimento
     * @return token
     * @throws ConversioneNonRiuscitaException se l'input non è una formula
     */
    static private Token RisolviToken(StringStream stream, TabellaDati tabella) throws ConversioneNonRiuscitaException {
        try {            
            char primo;
            do {
                primo = stream.read();
            } while (primo == ' ');
            
            switch (primo) {
                
                case '(':
                    return Token.APERTA_PARENTESI;
                
                case ')':
                    return Token.CHIUSA_PARENTESI;
                
                case ':':
                case '/':
                    return Token.DIVISIONE;
                
                case 'x':
                case '*':
                    return Token.MOLTIPLICAZIONE;
                
                case '+':
                    return Token.SOMMA;
                    
                case '-':
                    return Token.SOTTRAZIONE;
                    
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    stream.unreadChar();
                    Token numero = Token.NUMERO;
                    numero.valore = stream.readInt();
                    return numero;
                    
                case '$':
                    char colonnaR = stream.read();
                    if (colonnaR < 'A' || colonnaR > 'I') {
                        System.err.println("RIFERIMENTO ERRATO");
                        throw new ConversioneNonRiuscitaException();
                    }   
                    
                    int riga = stream.readInt();
                    int colonna = colonnaR - 'A' +1;
                    Token riferimento = Token.RIFERIMENTO;
                    Casella casella = tabella.getRoughValueAt(riga, colonna);
                    if (casella == null)
                        throw new ConversioneNonRiuscitaException();
                    try {
                        riferimento.valore = Double.parseDouble(casella.getRisultato());
                    } catch (NumberFormatException numberFormatException) {
                        throw new ConversioneNonRiuscitaException();
                    }
                    return riferimento;
                    
                default:
                    System.err.println("Carattere \""+primo+"\" non supportato nelle formule");
                    throw new ConversioneNonRiuscitaException();
            }
        } catch (IOException ex) {
            return Token.FINE_STREAM;
        }
    }
    
    /**
     * ritorna il valore della casella in formato double
     * @return risultato
     * @throws ConversioneNonRiuscitaException 
     */
    public double getRisutatoDouble() throws ConversioneNonRiuscitaException {
       return RisolviFormula(formula, tabella);
    }

    @Override
    public String getRisultato() {
        
        try {
            return ""+getRisutatoDouble();
        } catch (ConversioneNonRiuscitaException ex) {
            return "ERRORE: "+formula;
        }
    }
    
    
}
