package de.marcusschiesser.dbpendler.client.services.resources;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.util.Log;
import de.marcusschiesser.dbpendler.common.resources.ConnectionResource;
import de.marcusschiesser.dbpendler.common.vo.ConnectionVO;

public class ConnectionClientResource extends AbstractClientResource implements ConnectionResource {

	public ConnectionVO[] listDaily(String startId, String destinationId) {
		String responseText = doGet("connections", "start=" + startId + "&destination="
				+ destinationId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(responseText,
					ConnectionVO[].class);
		} catch (JsonParseException e) {
			Log.e(ConnectionClientResource.class.toString(), "parse error", e);
		} catch (JsonMappingException e) {
			Log.e(ConnectionClientResource.class.toString(), "mapping error", e);
		} catch (IOException e) {
			Log.e(ConnectionClientResource.class.toString(), "i/o error", e);
		}
		return new ConnectionVO[0];
	}

	public ConnectionVO[] listDay(String startId, String destinationId,
			String date) {
		// TODO Auto-generated method stub
		return null;
	}

}
