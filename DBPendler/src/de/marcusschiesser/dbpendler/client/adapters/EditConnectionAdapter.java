package de.marcusschiesser.dbpendler.client.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;

public final class EditConnectionAdapter extends ArrayAdapter<ConnectionVO> {
	public EditConnectionAdapter(Context context, int textViewResourceId,
			ConnectionVO[] result) {
		super(context, textViewResourceId, result);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View textView = super.getView(position, convertView, parent);
		ConnectionVO connection = getItem(position);
		String formated = String.format("%tR - %tR", connection.getStartTime(), connection.getDestinationTime());
		((TextView)textView).setText(formated);
		return textView;
	}
}