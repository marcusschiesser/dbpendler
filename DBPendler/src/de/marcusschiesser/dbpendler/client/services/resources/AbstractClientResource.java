package de.marcusschiesser.dbpendler.client.services.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class AbstractClientResource {

	private DefaultHttpClient client;
	private ResponseHandler<String> responseHandler;

	public AbstractClientResource() {
		super();
		client = new DefaultHttpClient();
		responseHandler = new BasicResponseHandler();
	}

	public String doGet(String path, String query) {
		try {
			// TODO: wie geht es parameter zu übergeben?
			URI uri;
			uri = URIUtils.createURI("http", "10.0.2.2", 8888,
					"rest/" + path, query, null);
			HttpGet get = new HttpGet(uri);
			HttpResponse response = client.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				return responseHandler.handleResponse(response);
			} else {
				Log.e(ConnectionClientResource.class.toString(), "wrong http status: " + statusCode);
			}
		} catch (URISyntaxException e) {
			Log.e(ConnectionClientResource.class.toString(), "uri syntax error",
					e);
		} catch (ClientProtocolException e) {
			Log.e(ConnectionClientResource.class.toString(), "protocol error",
					e);
		} catch (IOException e) {
			Log.e(ConnectionClientResource.class.toString(), "i/o error", e);
		}
		return null;
	}

}