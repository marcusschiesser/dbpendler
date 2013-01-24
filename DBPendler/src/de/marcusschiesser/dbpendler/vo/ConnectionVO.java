package de.marcusschiesser.dbpendler.vo;

import java.io.Serializable;
import java.util.Date;

public class ConnectionVO implements Serializable {
	private static final long serialVersionUID = 8594894128306553971L;
	
	public ConnectionVO(String start, String destination, Date startTime,
			Date destinationTime) {
		this.start = start;
		this.destination = destination;
		this.startTime = startTime;
		this.destinationTime = destinationTime;
	}
	public String start;
	public String destination;
	public Date startTime;
	public Date destinationTime;
	
}
