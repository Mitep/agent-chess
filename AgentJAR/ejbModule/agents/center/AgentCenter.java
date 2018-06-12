package agents.center;

import java.util.HashMap;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class AgentCenter implements AgentCenterLocal {

	private String alias;
	private String address;
	/**
	 *  agenti na ovom cvoru
	 */
	private HashMap<String, Object> agents;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
