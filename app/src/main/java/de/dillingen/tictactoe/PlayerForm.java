package de.dillingen.tictactoe;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class PlayerForm extends ActionBarActivity {

    private TextView spielernamen;
    private EditText spieler1;
    private EditText spieler2;

    public final static String SPIELER1 = "SPIELER1";
    public final static String SPIELER2 = "SPIELER2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_form);

        // Verbindungen zur View herstellen
        prepareViewObjects();

        // Daten aus Intent übernehmen
        spieler1.setText(getIntent().getExtras().getString(SPIELER1));
        spieler2.setText(getIntent().getExtras().getString(SPIELER2));
    }

    /** holt die Referenzen aus der View */
    protected void prepareViewObjects() {

        spielernamen = (TextView) this.findViewById(R.id.spielernamen);
        spieler1 = (EditText) this.findViewById(R.id.editspieler1);
        spieler2 = (EditText) this.findViewById(R.id.editspieler2);
    }

    /** überprüft, ob die Eingabe der Spieler erfolgt ist und beendet die Activity mit Rückgabe der
     * Spielernamen.
     */
    public void starteSpiel() {

        boolean valid = true;

        spieler1 = (EditText) this.findViewById(R.id.editspieler1);
        String spieler1name = spieler1.getText().toString();

        spieler2 = (EditText) this.findViewById(R.id.editspieler2);
        String spieler2name = spieler2.getText().toString();

        valid = (valid && (!spieler1name.isEmpty()));
        valid = (valid && (!spieler2name.isEmpty()));

        if (!valid) {

            Toast toast = Toast.makeText(this, "Bitte Namen für beide Spieler eingeben!", Toast.LENGTH_LONG);
            toast.show();

        } else {

            Intent intent = new Intent();
            intent.putExtra(SPIELER1, spieler1.getText().toString());
            intent.putExtra(SPIELER2, spieler2.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /** Ereignisbehandlung des Buttons StarteSpiel
     *
     * @param v
     */
    public void onClickStarteSpiel(View v) {
        starteSpiel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}