package de.marcusschiesser.dbpendler.client.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import de.marcusschiesser.dbpendler.client.services.resources.mocks.StationMockResource;
import de.marcusschiesser.dbpendler.common.resources.StationResource;
import de.marcusschiesser.dbpendler.common.vo.StationVO;

public class StationAdapter extends ArrayAdapter<StationVO> {

	private StationResource resource;

	public StationAdapter(Context context) {
		super(context, android.R.layout.simple_dropdown_item_1line);
		setNotifyOnChange(true);
		resource = new StationMockResource();
		StationVO[] stations = resource.getList(null);
		for (StationVO station : stations) {
			add(station);
		}
	}

}
