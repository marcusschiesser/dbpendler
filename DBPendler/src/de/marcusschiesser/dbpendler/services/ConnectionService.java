package de.marcusschiesser.dbpendler.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.os.AsyncTask;

import de.marcusschiesser.dbpendler.vo.ConnectionVO;

public class ConnectionService extends
		AsyncTask<String, Void, List<ConnectionVO>> {
	protected List<ConnectionVO> doInBackground(String... param) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getDailyTimes(param[0], param[1]);
	}
	 
	/**
	 * just a start of a mock, for real impl.:
	 * http://code.google.com/p/ksoap2-android/
	 * http://code.google.com/p/android-xmlrpc/
	 * http://code.google.com/p/android-json-rpc/ http://www.restlet.org/ Jersey
	 * (JSR-311) geht zwar aber probleme, kein offizieller android-patch
	 */
	private List<ConnectionVO> getDailyTimes(String start, String destination) {
		List<ConnectionVO> connectionVOs = new ArrayList<ConnectionVO>();
		if(start.trim().length()>0 && destination.trim().length()>0) {
			Random rand = new Random();
			for (int i = 0; i < 10; i++) {
				connectionVOs.add(new ConnectionVO(start, destination, new Date(0, 0,
						0, 8+i, rand.nextInt(30)), new Date(0, 0, 0, 9+i, 15+rand.nextInt(30))));				
			}
			return connectionVOs;
		} else
			return null;
	}
}
