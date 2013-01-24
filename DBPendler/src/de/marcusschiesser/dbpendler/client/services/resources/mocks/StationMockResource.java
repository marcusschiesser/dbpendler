package de.marcusschiesser.dbpendler.client.services.resources.mocks;

import java.util.HashMap;
import java.util.Map;

import de.marcusschiesser.dbpendler.common.resources.StationResource;
import de.marcusschiesser.dbpendler.common.vo.StationVO;

public class StationMockResource implements StationResource {

	static final String[] STATIONS = new String[] {

	"Aalen Bf", "Aulendorf Bf", "Baden-Baden Bf", "Biberach (Ri�) Bf",
			"Bruchsal Bf", "Crailsheim Bf", "Donaueschingen Bf",
			"Ellwangen Bf", "Freiburg (Breisgau) Hbf",
			"Friedrichshafen Stadt Bf", "Geislingen (Steige) Bf",
			"G�ppingen Bf", "Hausach Bf", "Heidelberg Hbf", "Horb Bf",
			"Hornberg Bf", "Karlsruhe Hbf", "Karlsruhe-Durlach", "Kehl Bf",
			"Konstanz Bf", "Mannheim Hbf", "Metzingen (W�rtt)", "M�hlacker Bf",
			"N�rtingen Bf", "Offenburg Bf", "Pforzheim Hbf", "Plochingen Bf",
			"Radolfzell Bf", "Ravensburg Bf", "Reutlingen Hbf", "Rottweil Bf",
			"Schorndorf Bf", "Schw�bisch Gm�nd Bf", "Singen (Hohentwiel) Bf",
			"Stuttgart Hbf", "Triberg Bf", "T�bingen Hbf", "Tuttlingen Bf",
			"Ulm Hbf", "Vaihingen (Enz) Bf", "Villingen Bf", "Weinheim Bf",
			"Wiesloch-Walldorf Bf", "Ansbach Bf", "Augsburg Hbf",
			"Berchtesgaden Hbf", "Buchloe Bf", "Donauw�rth Bf", "Fischen Bf",
			"Freilassing Bf", "F�rth (Bay) Hbf",
			"Steinach (b Rothenburg odT) Bf", "G�nzburg Bf", "Gunzenhausen Bf",
			"Immenstadt Bf", "Kaufbeuren Bf", "Kempten (Allg�u) Hbf",
			"Lindau Hbf", "Memmingen Bf", "M�nchen Hbf", "M�nchen Ostbf",
			"M�nchen-Pasing Bf", "Neumarkt (Oberpf) Bf", "N�rnberg Hbf",
			"Oberstdorf Bf", "Passau Hbf", "Plattling Bf", "Regensburg Hbf",
			"Rosenheim Bf", "Sonthofen Bf", "Traunstein Bf", "W�rzburg Hbf",
			"Angerm�nde Bf", "Berlin Hbf", "Berlin Ostbf",
			"Berlin Gesundbrunnen Bf", "Berlin-Spandau Bf",
			"Berlin S�dkreuz Bf", "Berlin-Wannsee Bf", "Bernau (b Berlin) Bf",
			"Brandenburg Hbf", "Cottbus Bf", "Eberswalde Hbf",
			"Elsterwerda Bf", "Forst (Lausitz) Bf", "Frankfurt (Oder) Bf",
			"L�bben (Spreewald) Bf", "L�bbenau (Spreewald) Bf", "Potsdam Hbf",
			"Prenzlau Bf", "Wittenberge Bf", "Bad Nauheim Bf", "Bebra Bf",
			"Bensheim Bf", "Darmstadt Hbf", "Dillenburg Bf",
			"Heppenheim (Bergstr) Bf", "Herborn Bf", "Frankfurt (Main) Hbf",
			"Frankfurt (Main) Flughafen Fernbf", "Frankfurt (Main) S�d",
			"Friedberg (Hess) Bf", "Fulda Bf", "Gie�en Bf", "Hanau Hbf",
			"Kassel-Wilhelmsh�he Bf", "Marburg (Lahn) Bf", "Treysa Bf",
			"Wabern Bf", "Wetzlar Bf", "Wiesbaden Hbf" };
	
	private Map<String, StationVO> result = null;
	
	private Map<String, StationVO> getStations() {
		if(result==null) {
			result = new HashMap<String, StationVO>(STATIONS.length);
			for (int i = 0; i < STATIONS.length; i++) {
				String stationName = STATIONS[i];
				StationVO station = new StationVO(stationName);
				result.put(stationName, station);
			}
		}
		return result;
	}

	public StationVO[] getList(String startsWith) {
		return getStations().values().toArray(new StationVO[STATIONS.length]);
	}

	public StationVO get(String id) {
		return getStations().get(id);
	}
}
