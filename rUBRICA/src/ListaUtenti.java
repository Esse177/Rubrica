import java.util.Vector;

public class ListaUtenti {
    private Vector<Utente> utenti;

    public ListaUtenti() {
        utenti = new Vector<>();
        utenti.add(new Utente("",""));
        utenti.add(new Utente("admin",""));
    }

    public void aggiungiUtente(Utente u) {
        utenti.add(u);
    }

    public Vector<Utente> getUtenti() {
        return utenti;
    }

}
