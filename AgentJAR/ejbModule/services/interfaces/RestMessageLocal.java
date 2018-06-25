package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import messages.ACLMessage;

@Local
public interface RestMessageLocal {

	// posalji acl poruku
	void postMessages(ACLMessage msg);
	
	// vrati listu performativa
	List<String> getMessages();
	
}
