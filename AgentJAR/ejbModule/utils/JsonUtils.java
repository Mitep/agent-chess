package utils;

import org.json.JSONObject;

import model.acl.ACLMessage;
import model.agent.AID;
import model.agent.AgentType;

/**
 * @author Nikola
 *
 */
public class JsonUtils {

	public static String getACLMessageString(ACLMessage msg) {
		JSONObject obj = new JSONObject();
		obj.append("type", "acl_message");
		obj.append("data", msg);
		return obj.toString();
	}

	public static String getAIDString(AID aid, boolean start) {
		JSONObject obj = new JSONObject();
		if(start)
			obj.append("type", "start_agent");
		else
			obj.append("type", "stop_agent");
		obj.append("data", aid);
		return obj.toString();
	}
	
	public static String getAgentType(AgentType at, boolean start) {
		JSONObject obj = new JSONObject();
		if(start)
			obj.append("type", "add_agent_type");
		else
			obj.append("type", "remove_agent_type");
		obj.append("data", at);
		return obj.toString();
	}

}
