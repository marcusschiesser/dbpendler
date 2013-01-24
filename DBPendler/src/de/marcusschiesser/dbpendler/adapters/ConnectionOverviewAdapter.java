package de.marcusschiesser.dbpendler.adapters;


import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import de.marcusschiesser.dbpendler.model.ConnectionsDb;
import de.marcusschiesser.dbpendler.vo.ConnectionVO;

public class ConnectionOverviewAdapter extends ResourceCursorAdapter {
	public ConnectionOverviewAdapter(Context context, int layout, Cursor c) {
		super(context, layout, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView textView = (TextView) view;
		ConnectionVO connectionVO = ConnectionsDb.createConnectionFromCursor(cursor);
		String formated = formatConnection(connectionVO);
		textView.setText(formated);
	}

	public static String formatConnection(ConnectionVO connectionVO) {
		String formated = String.format("%s %tR - %s %tR", connectionVO.start, connectionVO.startTime, connectionVO.destination, connectionVO.destinationTime);
		return formated;
	}
}