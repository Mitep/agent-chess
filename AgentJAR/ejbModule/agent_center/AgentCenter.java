package agent_center;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

@Singleton
@Startup
public class AgentCenter implements AgentCenterLocal , MessageListener {

	private String alias;
	private String address;
	
	public static final String LOOKUP = "";
	public static final String QUEUE = "";
	
	// mozda neka lista cvorova 
	
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
	
	@Override
	public void onMessage(Message arg0) {
		try {
			Context context = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) context.lookup(LOOKUP);
			final Queue queue = (Queue) context.lookup(QUEUE);
			context.close();
			Connection connection = cf.createConnection();
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			connection.start();
			
			String msgContent = arg0.getStringProperty("msgContent");

			TextMessage msg = session.createTextMessage(msgContent);
			// The sent timestamp acts as the message's ID
			long sent = System.currentTimeMillis();
			msg.setLongProperty("sent", sent);
			MessageProducer producer = session.createProducer(queue);
			producer.send(msg);
			producer.close();
			connection.stop();
			connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
