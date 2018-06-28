package services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agent_manager.AgentManagerLocal;
import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import services.interfaces.RestMessageLocal;

@Stateless
public class RestMessage implements RestMessageLocal {

	@Override
	public void postMessages(ACLMessage msg) {
		// posalji acl poruku
		try {
			final Properties env = new Properties();
			Context context = new InitialContext(env);
			ConnectionFactory cf = (ConnectionFactory) context.lookup("java:jboss/exported/jms/RemoteConnectionFactory");
			final Queue queue = (Queue) context.lookup("java:jboss/exported/jms/queue/mdbConsumerQueue");
			context.close();
			Connection connection = cf.createConnection();
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();;
			
			ObjectMessage tmsg = session.createObjectMessage((Serializable) msg);
			MessageProducer producer = session.createProducer(queue);
			producer.send(tmsg);	
			producer.close();
			connection.stop();
			connection.close();	
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getMessages() {
		// vrati listu performativa
		List<String> ret = new ArrayList<String>();
		for(Enum<Performative> e : Performative.values()) {
			ret.add(e.toString());
		}
		return ret;
	}

}
