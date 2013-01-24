package de.marcusschiesser.dbpendler.client;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import de.marcusschiesser.dbpendler.client.adapters.ConnectionOverviewAdapter;
import de.marcusschiesser.dbpendler.client.model.ConnectionsDb;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;

public class DBPendler extends ListActivity {
	public static final String EXTRA_SELECTED_CONNECTION = "SELECTED_CONNECTION";
	public static final String EXTRA_BOOKING_DATE = "BOOKING_DATE";

	private static final int CREATE_CONNECTION = 0;
	private static final int EDIT_CONNECTION = 1;

	private static final int EDIT_ID = Menu.FIRST;
	private static final int DELETE_ID = EDIT_ID + 1;
	private static final int DAY_DIALOG = 0;
	private static final int DAY_SELECT_DIALOG = 1;

	private ConnectionsDb mdbHelper;
	protected int mYear;
	protected int mMonth;
	protected int mDay;
	private ConnectionVO mSelectedItem;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mdbHelper = new ConnectionsDb(this);
		fillData();
		Button newConnectionButton = (Button) findViewById(R.id.main_new_connection);
		newConnectionButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DBPendler.this, EditConnection.class);
				startActivityForResult(intent, CREATE_CONNECTION);
			}
		});
		Button prefsButton = (Button) findViewById(R.id.main_preferences);
		prefsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(DBPendler.this, Preferences.class);
				startActivity(intent);
			}
		});

		registerForContextMenu(getListView());
	}

	private void fillData() {
		Cursor cursor = mdbHelper.fetchAllConnections();
		startManagingCursor(cursor);
		CursorAdapter connectionsAdapter = new ConnectionOverviewAdapter(this,
				android.R.layout.simple_list_item_1, cursor);
		setListAdapter(connectionsAdapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case CREATE_CONNECTION:
				fillData();
				break;
			case EDIT_CONNECTION:
				fillData();
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id) {
		case DAY_DIALOG:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.time_prompt);
			builder.setItems(R.array.time_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
//					String times[] = getResources().getStringArray(R.array.time_array);
//					String value = times[item];
			        final Calendar c = Calendar.getInstance();
			        if(item==1) {
						c.add(Calendar.DATE, 1);
			        }
			        mYear = c.get(Calendar.YEAR);
			        mMonth = c.get(Calendar.MONTH);
			        mDay = c.get(Calendar.DAY_OF_MONTH);
			        if(item==2) {
						showDialog(DAY_SELECT_DIALOG);
			        } else {
						Intent intent = new Intent(DBPendler.this, Confirmation.class);
						intent.putExtra(EXTRA_SELECTED_CONNECTION, mSelectedItem);
						intent.putExtra(EXTRA_BOOKING_DATE, c.getTime());
						startActivity(intent);
					}
				}
			});
			return builder.create();
		case DAY_SELECT_DIALOG:
	        return new DatePickerDialog(DBPendler.this,
	        		 new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
					Intent intent = new Intent(DBPendler.this, Confirmation.class);
					intent.putExtra(EXTRA_SELECTED_CONNECTION, mSelectedItem);
					Calendar c = Calendar.getInstance();
					c.set(mYear, mMonth, mDay);
					intent.putExtra(EXTRA_BOOKING_DATE, c.getTime());
					startActivity(intent);
                }
            }, mYear, mMonth, mDay);
		}
		return null;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = (Cursor) getListView().getItemAtPosition(position);
		mSelectedItem = ConnectionsDb.createConnectionFromCursor(cursor);
		showDialog(DAY_DIALOG);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo info) {
		super.onCreateContextMenu(menu, view, info);
		menu.add(Menu.NONE, EDIT_ID, Menu.NONE, R.string.menu_edit);
		menu.add(Menu.NONE, DELETE_ID, Menu.NONE, R.string.menu_delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case DELETE_ID:
			mdbHelper.deleteConnection(menuInfo.id);
			fillData();
			return true;
		case EDIT_ID:
			Intent i = new Intent(this, EditConnection.class);
			i.putExtra(ConnectionsDb.KEY_ROWID, menuInfo.id);
			startActivityForResult(i, EDIT_CONNECTION);
			return true;
		}
		return super.onContextItemSelected(item);
	}
}