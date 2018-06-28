package utils;

import org.json.JSONObject;

import model.acl.ACLMessage;
import model.agent.AID;

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

	public static String getAIDString(AID aid) {
		JSONObject obj = new JSONObject();
		obj.append("type", "aid");
		obj.append("data", aid);
		return obj.toString();
	}

}
