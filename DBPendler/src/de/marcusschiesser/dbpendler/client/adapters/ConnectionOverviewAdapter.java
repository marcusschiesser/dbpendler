package de.marcusschiesser.dbpendler.client.adapters;


import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import de.marcusschiesser.dbpendler.client.model.ConnectionsDb;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;

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
		String formated = String.format("%s %tR - %s %tR", connectionVO.getStart().getValue(), connectionVO.getStartTime(), connectionVO.getDestination().getValue(), connectionVO.getDestinationTime());
		return formated;
	}
}