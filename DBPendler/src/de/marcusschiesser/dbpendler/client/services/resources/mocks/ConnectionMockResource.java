package de.marcusschiesser.dbpendler.client.services.resources.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.marcusschiesser.dbpendler.common.resources.ConnectionResource;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;
import de.marcusschiesser.dbpendler.common.vo.StationVO;

public class ConnectionMockResource implements ConnectionResource {

	public ConnectionVO[] listDaily(String startId, String destinationId) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		StationMockResource resource = new StationMockResource();
		StationVO start = resource.get(startId);
		StationVO destination = resource.get(destinationId);
		List<ConnectionVO> connectionVOs = new ArrayList<ConnectionVO>();
		if(start != null && destination!=null) {
			Random rand = new Random();
			for (int i = 0; i < 10; i++) {
				connectionVOs.add(new ConnectionVO(start, destination, new Date(0, 0,
						0, 8+i, rand.nextInt(30)), new Date(0, 0, 0, 9+i, 15+rand.nextInt(30))));				
			}
			return connectionVOs.toArray(new ConnectionVO[connectionVOs.size()]);
		} else
			return new ConnectionVO[0];
	}

	public ConnectionVO[] listDay(String startId, String destinationId,
			String date) {
		return listDaily(startId, destinationId);
	}

}
