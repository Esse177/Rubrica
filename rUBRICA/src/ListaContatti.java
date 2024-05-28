//Filtro file:https://stackoverflow.com/questions/9092539/how-to-get-files-with-a-filename-starting-with-a-certain-letter
//Lettura File:https://www.w3schools.com/java/java_files_read.asp

import java.io.FilenameFilter;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class ListaContatti {
    private Vector<Persona> contatti;
    private String DIRECTORY_PATH = "informazioni";

    public ListaContatti() {
        contatti = new Vector<>();
        caricaDati();
    }

    public void aggiungiPersona(Persona p) {
        contatti.add(p);
        salvaDati();
    }

    public void modificaPersona(int index, Persona p) {
        contatti.set(index, p);
        salvaDati();
    }

    public void eliminaPersona(int index) {
        contatti.remove(index);
        salvaDati();
    }

    public Vector<Persona> getContatti() {
        return contatti;
    }

    //FILTER FOR FILES THAT START WITHH PERSONA AND END WITH TXT
    FilenameFilter startEnd = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.startsWith("Persona") && name.endsWith(".txt");
        }
    };

    //READ ALL FILE IN DIRECTORY PATH AND IF A FILE START WITH PERSONA AND END WITH TEXT SCANNER READ THE FILE AND CREATE A NEW PERSONA TO ADD TO CONTATTI
    private void caricaDati() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] files = directory.listFiles(startEnd);
        if (files != null) {
            for (File file : files) {
                try (Scanner scanner = new Scanner(file)) {
                    if (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] campi = line.split(";");
                        if (campi.length == 5) {
                            String nome = campi[0];
                            String cognome = campi[1];
                            String indirizzo = campi[2];
                            String telefono = campi[3];
                            int eta = Integer.parseInt(campi[4]);
                            Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
                            contatti.add(p);
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //UPDATE OF CONTACTS ON CONTATTI, SAVE SO WHEN THE APP IS OPEN CONTACTS WILL BE THERE
    private void salvaDati() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // DELETE ALL FILE
        File[] files = directory.listFiles(startEnd);
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        // SAVE ALL PERSONA + COUNT
        int count = 1;
        for (Persona p : contatti) {
            String fileName = DIRECTORY_PATH + "/Persona" + count + ".txt";
            try (PrintStream ps = new PrintStream(new File(fileName))) {
                ps.println(p.getNome() + ";" + p.getCognome() + ";" + p.getIndirizzo() + ";" + p.getTelefono() + ";" + p.getEta());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

}
