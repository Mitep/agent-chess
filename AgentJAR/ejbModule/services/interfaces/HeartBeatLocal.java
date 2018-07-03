package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import model.center.AgentCenter;

@Local
public interface HeartBeatLocal {
	
	public static String LOOKUP = "java:app/HeartBeat!services.interfaces.HearBeatLocal";
	
	public void startProtocol(List<AgentCenter> nodes);
	
}
