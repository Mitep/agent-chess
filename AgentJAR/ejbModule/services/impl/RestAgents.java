package services.impl;

import java.util.List;

import javax.ejb.Stateless;

import services.interfaces.RestAgentsLocal;

@Stateless
public class RestAgents implements RestAgentsLocal {

	@Override
	public List<String> getAgentsClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAgentsRunning() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAgentsRunning(String type, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAgentsRunning(String aid) {
		// TODO Auto-generated method stub
		
	}

}
