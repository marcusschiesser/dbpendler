package de.marcusschiesser.dbpendler.client.services;

import android.os.AsyncTask;
import de.marcusschiesser.dbpendler.client.services.resources.ConnectionClientResource;
import de.marcusschiesser.dbpendler.client.services.resources.mocks.ConnectionMockResource;
import de.marcusschiesser.dbpendler.common.resources.ConnectionResource;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;

public class ConnectionService extends
		AsyncTask<String, Void, ConnectionVO[]> {
	private static final boolean USE_MOCKS = true;

	protected ConnectionVO[] doInBackground(String... param) {
		ConnectionResource resource;
		if(USE_MOCKS) {
			resource = new ConnectionMockResource();
		} else {
			resource = new ConnectionClientResource();
		}
		return resource.listDaily(param[0], param[1]);
	}
	 
}
