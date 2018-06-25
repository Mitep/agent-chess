package services.interfaces;

import javax.ejb.Local;

@Local
public interface RestNodeLocal {

	// nemaster kontaktira master da ga registruje
	void postNode(String slaveAddress);
	
	
	
}
