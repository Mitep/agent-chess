package messages;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agents.AID;

/**
 * Message-Driven Bean implementation class for: MDBConsumer
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/chatAppQueue") })
public class MDBConsumer implements MessageListener {

	private static final String REMOTE_FACTORY = "REMOTE_FACTORY";
	private static final String CHATAPP_QUEUE = "CHATAPP_QUEUE";
	
	/**
	 * Default constructor.
	 */
	public MDBConsumer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			ACLMessage msg = (ACLMessage) message.getObjectProperty("acl_message");
			AID[] receivers = msg.getReceivers();
			for(AID a : receivers) {
				Context context = new InitialContext();
				
				ConnectionFactory cf = (ConnectionFactory) context.lookup(REMOTE_FACTORY);
				
				
				final Queue queue = (Queue) context.lookup(CHATAPP_QUEUE);
				context.close();
				Connection connection = cf.createConnection();
				final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
