package de.marcusschiesser.dbpendler;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import de.marcusschiesser.dbpendler.adapters.ConnectionOverviewAdapter;
import de.marcusschiesser.dbpendler.client.R;
import de.marcusschiesser.dbpendler.vo.ConnectionVO;

public class Confirmation extends Activity {
	private static final int SUCCESS_DIALOG = 0;
	private TextView mPrice;
	private TextView mSeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation);
		
		Bundle extras = getIntent().getExtras();
		ConnectionVO connection = (ConnectionVO) extras.getSerializable(DBPendler.EXTRA_SELECTED_CONNECTION);
		Date bookingDate = (Date) extras.getSerializable(DBPendler.EXTRA_BOOKING_DATE);
		
		TextView t = (TextView) findViewById(R.id.confirmation_connection);
		t.setText(ConnectionOverviewAdapter.formatConnection(connection));
		
		t = (TextView) findViewById(R.id.confirmation_day);
		t.setText(DateFormat.format("dd.MM.yyyy", bookingDate));
		
		t = (TextView) findViewById(R.id.confirmation_bahncard);
		t.setText("Bahncard: nein");
		
		mSeat = (TextView) findViewById(R.id.confirmation_seat);
		mSeat.setText("");
		
		mPrice = (TextView) findViewById(R.id.confirmation_price);
		mPrice.setText("18,50 Ä");
		
		CheckBox reserveSeat = (CheckBox) findViewById(R.id.confirmation_reserve_seat);
		reserveSeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					mSeat.setText("Reservierung: Groﬂraum Gang");
					mPrice.setText("21,00 Ä");
				} else {
					mSeat.setText("");
					mPrice.setText("18,50 Ä");
				};
			}
		});
		
		Button bookButton = (Button) findViewById(R.id.confirmation_book);
		bookButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(SUCCESS_DIALOG);
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case SUCCESS_DIALOG:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.success_title);
			builder.setMessage(R.string.success_message);
			builder.setPositiveButton(R.string.confirmation_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					setResult(RESULT_OK);
					finish();
				}
			});
			return builder.create();
		}
		return null;
	}
}
