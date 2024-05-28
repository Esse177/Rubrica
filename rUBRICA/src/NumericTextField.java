//SOURCE:https://coderanch.com/t/333674/java/JTextField-Integer-values

import javax.swing.JTextField;
/**
 * This text field only allows positive numbers to be entered. No letters or negative numbers will be allowed.
 *
 * @version 1.0  4-Oct-2001
 */
public class NumericTextField extends JTextField {
    public int constraint;
    /**
     * This constructor takes the length of the textfield, and the constraint of the length of the data.
     *
     * @param int cols length of the textfield.
     * @param int c constraint of the length of data.
     */
    public NumericTextField(int cols, int c) {
        super(cols);
        constraint = c;
        setDocument(new NumericDocument(constraint));
    }
}