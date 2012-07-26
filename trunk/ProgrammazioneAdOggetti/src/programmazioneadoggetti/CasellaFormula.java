/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programmazioneadoggetti;

import java.io.IOException;

/**
 * Estende la classe casella
 * @author miriam
 */
public class CasellaFormula extends Casella {
    private String formula;
    private TabellaDati tabella;

    public CasellaFormula(String formula, TabellaDati tabella) throws ConversioneNonRiuscitaException {
        this.formula = formula;
        RisolviFormula(formula, tabella);
        this.tabella = tabella;
    }

    @Override
    public String getFormula() {
        return formula;
    }
    
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
        
        public double valore;
        
        public boolean isOperator() {
            switch (this) {
                case SOMMA:
                case SOTTRAZIONE:
                case MOLTIPLICAZIONE:
                case DIVISIONE:
                case APERTA_PARENTESI:
                    return true;
                default:
                    return false;
            }
        }
        
        public boolean isNumber() {
            switch (this) {
                case NUMERO:
                case RIFERIMENTO:
                case CHIUSA_PARENTESI:
                    return true;
                default:
                    return false;
            }
        }
    }
            
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
     * funziona interna che aiuta RisolviFormula
     * @param stream
     * @param tabella
     * @param lastVal
     * @param lastToken
     * @return
     * @throws ConversioneNonRiuscitaException 
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
            
            /*case APERTA_PARENTESI:
                if (!lastToken.isOperator()) {
                    System.err.println("PARENTESI: operatore precedente non operatore");
                    throw new ConversioneNonRiuscitaException();
                }
                //return lastVal - RisolviFormulaRic(stream, tabella, 0,token);
                
                double internoParentesi = RisolviFormulaRic(stream, tabella, 0, token);
                if (!RisolviToken(stream, tabella).equals(Token.CHIUSA_PARENTESI)) {
                    System.err.println("non hai chiuso le parentesi per bene");
                    throw new ConversioneNonRiuscitaException();
                }
                
                return internoParentesi;*/
                
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
     * @param stream
     * @param tabella
     * @return token
     * @throws ConversioneNonRiuscitaException 
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
    
    /*
    static public double RisolviFormula(String formula, TabellaDati tabella) throws ConversioneNonRiuscitaException {
        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(formula.getBytes()));
        
        try {
            if (stream.readChar() != '=')
                throw new ConversioneNonRiuscitaException();
        } catch (IOException ex) {
            throw new ConversioneNonRiuscitaException();
        }
        
        //StringReader stream = new StringReader(formula);
        
        double risultato = 0;
        double prec = 0;
        char precOP = '='; 
        /* precOP può significare
         * 
         * = appena cominciato
         * i letto un numero
         * r letto un riferimento
         * + somma
         * - sottrazione
         * / divisione
         * * moltiplicazione
         * 
         *
        
        for (int i=1; i<formula.length(); i++) {
            stream.mark(5000);
            int x,y;
            char switcher = stream.readChar();
            switch (switcher) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                    try {
                        stream.reset();
                        prec = stream.readDouble();
                    } catch (IOException ex) {
                        throw new ConversioneNonRiuscitaException();
                    }
                    precOP = 'i';
                    break;
                    
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                    x=switcher-'A'+1;
                    try {
                        y=stream.readInt();
                    } catch (IOException ex) {
                        throw new ConversioneNonRiuscitaException();
                    }
                    try {
                        prec = ((CasellaFormula)tabella.getRoughValueAt(y, x)).getRisutatoDouble();
                    } catch (ClassCastException) {
                        throw new ConversioneNonRiuscitaException();
                    }
                    precOP = 'r';
                    break;
                    
                case '+':
                    if (precOP != 'i' || precOP != 'r')
                        throw new ConversioneNonRiuscitaException();
                    risultato += prec;
                    precOP = '+';
                    break;
                case '-':
                    if (precOP != 'i' || precOP != 'r')
                        throw new ConversioneNonRiuscitaException();
                    break;
                case '*':
                    if (precOP != 'i' || precOP != 'r')
                        throw new ConversioneNonRiuscitaException();
                    break;
                case '/':
                    if (precOP != 'i' || precOP != 'r')
                        throw new ConversioneNonRiuscitaException();
                    break;
                   
                    
                case ' ':
                    break;
                default:
                    throw new ConversioneNonRiuscitaException();
            }
        }
        
        return risultato;
    }
    * 
    */
    
    public double getRisutatoDouble() throws ConversioneNonRiuscitaException {
        //TODO if not da ricalcolare non ricalcolare!
        
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
