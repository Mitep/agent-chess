package services.impl;

import java.util.List;

import javax.ejb.Stateless;

import model.acl.ACLMessage;
import services.interfaces.RestMessageLocal;

@Stateless
public class RestMessage implements RestMessageLocal {

	@Override
	public void postMessages(ACLMessage msg) {
		// posalji acl poruku
		
	}

	@Override
	public List<String> getMessages() {
		// vrati listu performativa
		
		return null;
	}

}
