package services.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.TimerService;

import model.center.AgentCenter;
import services.interfaces.HeartBeatLocal;
import utils.RestBuilder;

@Singleton
public class HeartBeat implements HeartBeatLocal {

	@Resource
	TimerService ts;
	
	private List<AgentCenter> nodes;
	
	@Override
	public void startProtocol(List<AgentCenter> nodes) {
		this.nodes = nodes;
	}
	
	@Schedules({
		@Schedule(hour = "*", minute = "*/1", second = "*", info = "every minute")
		})
	private void heartBeatPr() {
		System.out.println("###### Heartbeat ######");
		for(AgentCenter a : nodes) {
			if(RestBuilder.getHeartBeat(a) == false) {
				if(RestBuilder.getHeartBeat(a) == false) {
					// brisemo ovaj cvor
				}
			}
		}
	}
		

}
