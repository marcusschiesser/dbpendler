package de.marcusschiesser.dbpendler.client;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.marcusschiesser.dbpendler.client.adapters.EditConnectionAdapter;
import de.marcusschiesser.dbpendler.client.adapters.StationAdapter;
import de.marcusschiesser.dbpendler.client.model.ConnectionsDb;
import de.marcusschiesser.dbpendler.client.services.ConnectionService;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;

public class EditConnection extends ListActivity {

	private AutoCompleteTextView mStartText;
	private AutoCompleteTextView mDestinationText;
	private Long mRowId;
	private ConnectionsDb mConnectionsDb;
	private int mClickPosition;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_connection);
		mConnectionsDb = new ConnectionsDb(this);

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setMessage(getResources().getString(R.string.edit_waiting));
		mProgressDialog.setCancelable(false);
		mProgressDialog.setIndeterminate(true);
		
		mStartText = (AutoCompleteTextView) findViewById(R.id.edit_start);
		StationAdapter adapter = new StationAdapter(this);
		AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				updateList();
			}
		};
		mStartText.setAdapter(adapter);
		mStartText.setOnItemClickListener(itemListener);
		mDestinationText = (AutoCompleteTextView) findViewById(R.id.edit_destination);
		mDestinationText.setAdapter(adapter);
		mDestinationText.setOnItemClickListener(itemListener);

		// get row id from savedinstance or intent
		mRowId = (savedInstanceState == null) ? null
				: (Long) savedInstanceState
						.getSerializable(ConnectionsDb.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(ConnectionsDb.KEY_ROWID)
					: null;
		}

		populateFields();

		Button okButton = (Button) findViewById(R.id.edit_ok);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(commitData()) {
					setResult(RESULT_OK);
					finish();
				}
			}
		});
		Button cancelButton = (Button) findViewById(R.id.edit_cancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
	
    public void updateList() {
    	String start = mStartText.getText().toString();
		String destination = mDestinationText.getText().toString();
		if(start.trim().length()>0 && destination.trim().length()>0) {
			
			ConnectionService service = new ConnectionService() {
	    		@Override
	    		protected void onPreExecute() {
	    			mProgressDialog.show();
	    		}
				@Override
				protected void onPostExecute(ConnectionVO[] result) {
					mProgressDialog.dismiss();
					if(result!=null) {
						setListAdapter(new EditConnectionAdapter(EditConnection.this, android.R.layout.simple_list_item_1, result));
					} else {
						setListAdapter(null);
					}
				}
	    	};
	    	
			service.execute(start, destination);
		}
	}

	private void populateFields() {
        if (mRowId != null) {
            Cursor note = mConnectionsDb.fetchConnection(mRowId);
            startManagingCursor(note);
            mStartText.setText(note.getString(
                    note.getColumnIndexOrThrow(ConnectionsDb.KEY_START)));
            mDestinationText.setText(note.getString(
                    note.getColumnIndexOrThrow(ConnectionsDb.KEY_DESTINATION)));
            updateList();
        }
    }

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mConnectionsDb.close();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ConnectionsDb.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private boolean commitData() {
        ListView listView = getListView();
        ConnectionVO selectedItem = (ConnectionVO) listView.getItemAtPosition(mClickPosition);

        if(selectedItem==null) {
        	Toast.makeText(this, R.string.edit_no_connection, Toast.LENGTH_LONG).show();
        	return false;
        } else {
        	if (mRowId == null) {
    			long id = mConnectionsDb.createConnection(selectedItem);
                if (id > 0) {
                    mRowId = id;
                }
            } else {
                mConnectionsDb.updateConnection(mRowId, selectedItem);
            }
        	return true;
        } 
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	mClickPosition = position;
    	setSelection(mClickPosition);
    }
}
