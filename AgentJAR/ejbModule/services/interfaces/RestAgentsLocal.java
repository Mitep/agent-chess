package services.interfaces;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RestAgentsLocal {

	List<String> getAgentsClasses();
	List<String> getAgentsRunning();
	void putAgentsRunning(String type, String name);
	void deleteAgentsRunning(String aid);
	
}
