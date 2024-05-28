//SOURCE NON EDITABLE CELL FOR JTABLE:https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    //NEED TO BE DEFINED FOR USE FROM OTHER FUNCTION
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable personaTable;
    private ListaContatti contatti;

    public GUI() {
        //CREATION LISTACONTATTI
        contatti = new ListaContatti();
        //GUI TO SHOW CONTACTS
        tableModel = new DefaultTableModel(new String[]{"Nome", "Cognome", "Telefono"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        personaTable = new JTable(tableModel);

        for (Persona p : contatti.getContatti()) {
            tableModel.addRow(new String[]{p.getNome(), p.getCognome(), p.getTelefono()});
        }

        //MAIN FRAME
        frame = new JFrame("Rubrica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //MUST BE DEFINED OTHERWISE KEEP RUNNING IN BACKGROUND
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        //SCROLL PANE OF CONTACTS
        JScrollPane scrollPane = new JScrollPane(personaTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        //TOOLBAR
        JToolBar toolBar = new JToolBar();

        //BUTTONS
        JButton nuovoButton = new JButton("Nuovo");
        JButton modificaButton = new JButton("Modifica");
        JButton eliminaButton = new JButton("Elimina");

        //UPDATE TOOLBAR AND FRAME
        toolBar.add(nuovoButton);
        toolBar.add(modificaButton);
        toolBar.add(eliminaButton);
        frame.add(toolBar,BorderLayout.NORTH);

        //WHEN WE NEED TO CCREATE A NEW CONTACT WE OPEN A DIALOG WINDOW
        nuovoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apriEditorPersona(null,-1);
            }
        });

        //WHEN WE NEED TO MODIFY AN EXISTENT CONTACT WE OPEN A DIALOG WINDOW
        modificaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = personaTable.getSelectedRow();
                if (selectedRow != -1) {
                    Persona persona = contatti.getContatti().get(selectedRow);
                    apriEditorPersona(persona,selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Seleziona una persona da modificare.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //WHEN A CONTACT IS DELETED WE REMOVE THE ELEMENTS FROM THE TABLE AND UPDATE LISTACONTATTI
        eliminaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = personaTable.getSelectedRow();
                if (selectedRow != -1) {
                    int confirmation = JOptionPane.showConfirmDialog(frame,
                            "Eliminare la persona " + tableModel.getValueAt(selectedRow, 0) + " " + tableModel.getValueAt(selectedRow, 1) + "?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        contatti.eliminaPersona(selectedRow);
                        tableModel.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Seleziona una persona da eliminare.");
                }
            }
        });

        frame.setVisible(true);
    }

    private void apriEditorPersona(Persona persona, int index) {

        JDialog editorDialog = new JDialog(frame, "Editor Persona", true);
        editorDialog.setSize(300, 300);

        editorDialog.setLayout(new BorderLayout());
        JPanel dialogPanel = new JPanel(new GridLayout(5,2));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();
        JLabel cognomeLabel = new JLabel("Cognome:");
        JTextField cognomeField = new JTextField();
        JLabel indirizzoLabel = new JLabel("Indirizzo:");
        JTextField indirizzoField = new JTextField();
        JLabel telefonoLabel = new JLabel("Telefono:");
        JTextField telefonoField = new JTextField();
        JLabel etaLabel = new JLabel("Età:");
        NumericTextField etaField = new NumericTextField(1,5);

        JButton salvaButton = new JButton("Salva");
        JButton annullaButton = new JButton("Annulla");

        //IF WE ARE MODYFING A PERSON UPDATE FIELDS WITH INFORMATIONS
        if (persona != null) {
            nomeField.setText(persona.getNome());
            cognomeField.setText(persona.getCognome());
            indirizzoField.setText(persona.getIndirizzo());
            telefonoField.setText(persona.getTelefono());
            etaField.setText(String.valueOf(persona.getEta()));
        }

        salvaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String indirizzo = indirizzoField.getText();
                String telefono = telefonoField.getText();
                int eta = Integer.parseInt(etaField.getText());

                //IF WE CREATED A NEW CONTACT WE UPDATE LISTACONTATTI
                if (persona == null) {
                    Persona nuovapersona = new Persona(nome,cognome,indirizzo,telefono,eta);
                    contatti.aggiungiPersona(nuovapersona);
                    tableModel.addRow(new Object[]{nome, cognome, telefono});
                } else {
                    //OTHERWISE WE UPDATE THE PERSONA ALREADY EXISTENT
                    int selectedRow = personaTable.getSelectedRow();
                    tableModel.setValueAt(nome, selectedRow, 0);
                    tableModel.setValueAt(cognome, selectedRow, 1);
                    tableModel.setValueAt(telefono, selectedRow, 2);

                    persona.setNome(nome);
                    persona.setCognome(cognome);
                    persona.setIndirizzo(indirizzo);
                    persona.setTelefono(telefono);
                    persona.setEta(eta);

                    contatti.modificaPersona(selectedRow,persona);
                }

                editorDialog.dispose();
            }
        });

        //JUST CLOSE THE WINDOW WITHOUT DOING NOTHING
        annullaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorDialog.dispose();
            }
        });

        //ADD ALL BUTTON AND TOOLBAR
        dialogPanel.add(nomeLabel);
        dialogPanel.add(nomeField);
        dialogPanel.add(cognomeLabel);
        dialogPanel.add(cognomeField);
        dialogPanel.add(indirizzoLabel);
        dialogPanel.add(indirizzoField);
        dialogPanel.add(telefonoLabel);
        dialogPanel.add(telefonoField);
        dialogPanel.add(etaLabel);
        dialogPanel.add(etaField);
        editorDialog.add(dialogPanel,BorderLayout.CENTER);

        JToolBar toolBar = new JToolBar();
        toolBar.add(salvaButton);
        toolBar.add(annullaButton);
        editorDialog.add(toolBar,BorderLayout.NORTH);

        editorDialog.setVisible(true);
    }
}
