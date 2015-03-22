package com.elvis.elvis.calcolatoreiva;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;


public class MainActivity extends ActionBarActivity {
    private static double iva = 0.22;

    // Currency and percent formatters
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double importo = 0.0; // L'importo inserito dall'utente
    private double customPercent = 0.1; // Percentuale iniziale iva
    private TextView importoDisplayTextView; // Visualizza l'importo nel formato appropriato
    private TextView percentCustomTextView; // Visualizza la percentuale personalizzata dell'IVA
    private TextView iva22TextView; // Visualizza L'IVA al 22%
    private TextView valoreNetto22TextView; // Visualizza il valore al netto dell?IVA al 22%
    private TextView ivaCustomTextView; // Visualizza il valore personalizzato dell'IVA
    private TextView valoreNettoCustomTextView; // Visualizza il valore al netto dell'iva alla percentuale personalizzata

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recupera i riferimenti alle textView
        importoDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentualeCustomTextView);
        iva22TextView = (TextView) findViewById(R.id.iva22TextView);
        valoreNetto22TextView = (TextView) findViewById(R.id.valoreNetto22TextView);
        ivaCustomTextView = (TextView) findViewById(R.id.ivaCustomTextView);
        valoreNettoCustomTextView = (TextView) findViewById(R.id.valoreNettoCustomTextView);

        // Aggiorna la GUI in base all'importo e della percentuale custom
        importoDisplayTextView.setText(currencyFormat.format(importo));
        updateStandard();
        updateCustom();

        // Imposta il TextWatcher per importoEditText
        EditText importoEditText = (EditText) findViewById(R.id.importoEditText);
        importoEditText.addTextChangedListener(importoEditTextWatcher);

        // Imposto il listener per la seekBar
        SeekBar customIvaSeekBaar = (SeekBar) findViewById(R.id.ivaCustomSeekBar);
        customIvaSeekBaar.setOnSeekBarChangeListener(customSeekbarListener);
    }

    // Aggiorna le TextView dell'IVA al 22%
    private void updateStandard() {
        // Calcolo l'IVA al 22% e l'importo al netto dell'IVA
        double iva22 = importo * iva;
        double nettoIva22 = importo - iva22;

        // Visualizza l'iva al 22 e il netto formattato come valuta
        iva22TextView.setText(currencyFormat.format(iva22));
        valoreNetto22TextView.setText((currencyFormat.format(nettoIva22)));
    }

    private void updateCustom() {
        // Visualizza la percentuale custom formattata correttamente
        percentCustomTextView.setText(percentFormat.format(customPercent));

        // Calcola l'iva e l'importo custom
        double ivaCustom = importo * customPercent;
        double importoCustom = importo - ivaCustom;

        // Visualizza l'iva e l'importo custom
        ivaCustomTextView.setText(currencyFormat.format(ivaCustom));
        valoreNettoCustomTextView.setText(currencyFormat.format(importoCustom));
    }

    // Chiamata quando l'utente modifica la posizione della seekBar
    private SeekBar.OnSeekBarChangeListener customSeekbarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // Imposta customPercent in base al valore scelto
            customPercent = progress / 100.0;
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // L'ogetto che risponde agli eventi di importoEditText
    private TextWatcher importoEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Converte il testo inserito in double
            try {
                importo = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                // In caso di eccezione, imposta l'importo a 0
                importo = 0.0;
            }

            // Visaualizza l'importo formattato correttamente
            importoDisplayTextView.setText(currencyFormat.format(importo));
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
