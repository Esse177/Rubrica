//SOURCE:https://coderanch.com/t/333674/java/JTextField-Integer-values

import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
/**
 * This class creates a document class that will only allow number up to a certain length
 *
 * @version 1.0  4-Oct-2001
 */
public class NumericDocument extends PlainDocument {
    public int constraint;
    /**
     * This constructor takes the constraint of the length of the data.
     *
     * @param int c constraint of the length of data.
     */
    public NumericDocument(int c) {
        super();
        constraint = c;
    }
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        char[] insertChars = str.toCharArray();
        boolean valid = true;
        boolean fit = true;
        if(insertChars.length + getLength() <= constraint) {
            for(int i = 0; i < insertChars.length; i++) {
                if(!Character.isDigit(insertChars[i])) {
                    valid = false;
                    break;
                }
            }
        } else {
            fit = false;
        }
        if (fit && valid){
            super.insertString(offset,str,a);
        }
    }
}