package de.marcusschiesser.dbpendler.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.marcusschiesser.dbpendler.vo.ConnectionVO;

public final class EditConnectionAdapter extends ArrayAdapter<ConnectionVO> {
	public EditConnectionAdapter(Context context, int textViewResourceId,
			List<ConnectionVO> objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View textView = super.getView(position, convertView, parent);
		ConnectionVO connection = getItem(position);
		String formated = String.format("%tR - %tR", connection.startTime, connection.destinationTime);
		((TextView)textView).setText(formated);
		return textView;
	}
}