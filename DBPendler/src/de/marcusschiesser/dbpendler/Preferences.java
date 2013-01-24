package de.marcusschiesser.dbpendler;

import de.marcusschiesser.dbpendler.client.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Preferences extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		setTitle(R.string.prefs_title);

		Spinner bahncardSpinner = (Spinner) findViewById(R.id.prefs_bahncard);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.prefs_bahncard_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bahncardSpinner.setAdapter(adapter);
		
		Spinner seatSpinner = (Spinner) findViewById(R.id.prefs_seat);
		ArrayAdapter<CharSequence> seatAdapter = ArrayAdapter.createFromResource(
				this, R.array.prefs_seat_array,
				android.R.layout.simple_spinner_item);
		seatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		seatSpinner.setAdapter(seatAdapter);
		
		Button okButton = (Button) findViewById(R.id.prefs_ok);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});

	}
}
