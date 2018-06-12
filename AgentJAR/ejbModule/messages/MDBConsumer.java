package messages;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message-Driven Bean implementation class for: MDBConsumer
 */
@MessageDriven(activationConfig = { 
        @ActivationConfigProperty(propertyName = "destinationType", 
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination",
                propertyValue="queue/chatAppQueue")                
})
public class MDBConsumer implements MessageListener {

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
        // TODO lookup agenta i slanje poruke
        
    }

}
