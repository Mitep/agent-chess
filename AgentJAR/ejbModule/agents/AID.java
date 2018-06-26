package agents;

public class AID {

	private String name;
	private String host;
	private AgentType type;

	public AID() {
		super();
		name = "";
		host = "";
		type = null;
	}

	public AID(String name, String host, AgentType type) {
		super();
		this.name = name;
		this.host = host;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public AgentType getType() {
		return type;
	}

	public void setType(AgentType type) {
		this.type = type;
	}

}
