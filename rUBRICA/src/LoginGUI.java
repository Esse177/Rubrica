import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {

    private ListaUtenti listaUtenti;

    public LoginGUI() {
        //LIST OF USER THAT CAN LOG IN
        listaUtenti = new ListaUtenti();

        //MAIN FRAME
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel loginPanel = new JPanel(new GridLayout(2, 2));

        ///LABELS AND FIELDS
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        //BUTTONS
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (autenticaUtente(username, password)) {
                    new GUI();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //ADD ELEMENTS TO UI
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        JToolBar toolBar = new JToolBar();
        toolBar.add(loginButton);
        frame.add(loginPanel, BorderLayout.CENTER);
        frame.add(toolBar, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    //VERIFY THAT USERNAME AND PASSWORD ARE VALID
    private boolean autenticaUtente(String username, String password) {
        for (Utente u : listaUtenti.getUtenti()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
