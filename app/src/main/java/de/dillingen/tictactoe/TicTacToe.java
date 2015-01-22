package de.dillingen.tictactoe;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class TicTacToe extends ActionBarActivity implements View.OnClickListener {

    // Wer ist dran? Wer hat gewonnen?
    private TextView status;
    // Buttons für das Spielfeld
    private Button[][] t;
    // Daten für die momentane Spielfeldbelegung und Programmlogik
    private Matrix m;
    // Zustandsvariable für das Spiel
    private int zustand = 0; // 0 = Spiel gestartet, 1 = Spiel läuft, 2 = Spiel ist zu Ende.

    private String[] spielernamen;

    public static final int REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // prepare View.
        prepareViewObjects();

        // Matrix objekt erzeugen.
        m = new Matrix(3);

        // Array für Spieler erzeugen und Eingabe der Spieler starten.
        spielernamen = new String[2];
        spielernamen[0] = "";
        spielernamen[1] = "";

        // Bei Start Spieleraufforderung.
        openPlayerForm();
    }

    /**
     * holt die Referenzen aus der View und fügt diese Activity als Actionlistener hinzu
     */
    protected void prepareViewObjects() {

        status = (TextView) findViewById(R.id.status);

        // Referenzen für Buttons.
        t = new Button[3][3];
        t[0][0] = (Button) findViewById(R.id.r00);
        t[0][1] = (Button) findViewById(R.id.r01);
        t[0][2] = (Button) findViewById(R.id.r02);
        t[1][0] = (Button) findViewById(R.id.r10);
        t[1][1] = (Button) findViewById(R.id.r11);
        t[1][2] = (Button) findViewById(R.id.r12);
        t[2][0] = (Button) findViewById(R.id.r20);
        t[2][1] = (Button) findViewById(R.id.r21);
        t[2][2] = (Button) findViewById(R.id.r22);

        // Actionlistener hinzufügen.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                t[i][j].setOnClickListener(this);
            }
        }
    }

    /**
     * ruft die zweite Activity auf. Der Aufruf über die Methode startActivityForResult bewirkt, dass
     * einen Aufruf der Methode onActivityResult, wenn die zweite (überdeckende) Activity geschlossen
     * wird (d. h. dort die Methode finish() aufgerufen wird.
     */
    public void openPlayerForm() {

        Intent intent = new Intent(this, PlayerForm.class);
        intent.putExtra(PlayerForm.SPIELER1, spielernamen[0]);
        intent.putExtra(PlayerForm.SPIELER2, spielernamen[1]);
        startActivityForResult(intent, REQUEST_ID);
    }

    /** übernimmt die eingegebenen Spielernamen und startet neues Spiel
     *
     * @param requestId der Wert, der beim Aufruf der überdeckenden Activity übergeben wurde
     * @param resultCode ein Wert, den die überdeckende Activity setzt, um Erfolg/Misserfolg zu übermitteln
     * @param intent der Intent, der die zurückgegebenen Daten enthält
     */
     @Override
     protected void onActivityResult(int requestId, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK && requestId == REQUEST_ID) {

            spielernamen[0] = intent.getExtras().getString(PlayerForm.SPIELER1);
            spielernamen[1] = intent.getExtras().getString(PlayerForm.SPIELER2);
            neuesSpiel();
        }
    }

    /**
     * setzt die Zustandsvariable und alle Werte in der Matrix auf 0
     */
    private void neuesSpiel() {

        m.newGame();
        updateSpielfeld();
        updateStatus();
        // Spielfeld zurückgesetzt, setze Anfangszustand.
        zustand = 0;
    }

    /** holt die aktuelle Spielfeldbelegung und aktualisiert die View */
    private void updateSpielfeld() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                t[i][j].setText(m.getSymbol(i, j));
            }
        }

    }

    /** überprüft, ob ein Spieler gewonnen hat und aktualisiert den Status */
    public void updateStatus() {

        int playerwon = m.checkGame();

        if (playerwon == 0) {

            status.setText("nächster Zug: Spieler " + spielernamen[m.getActivePlayer() - 1] +
                    " (" + m.getSymbol(m.getActivePlayer()) + ")");
        } else {

            String text = "Spieler " + spielernamen[m.getActivePlayer() - 1] + " hat gewonnen";
            status.setText(text);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /** Ereignisbehandlung des Buttons "Spiel starten"
     *
     * @param v
     */
    public void onClickNeuesSpiel(View v) {
        neuesSpiel();
    }

    /** Ereignisbhandlung für die Buttons des Spielfelds
     *
     * @param v
     */
    public void onClick(View v) {

        Button pressed = (Button) v.findViewById(v.getId());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (t[i][j] == pressed) {
                    m.setSymbol(i, j);
                    updateStatus();
                    updateSpielfeld();
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tic_tac_toe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_editspieler) {
            openPlayerForm();
        }
        return super.onOptionsItemSelected(item);
    }
}
