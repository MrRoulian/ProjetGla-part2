package livraison;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.criteria.Order;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
    propertyName = "acknowledgeMode",
    propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(
    propertyName = "destinationType",
    propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(
    propertyName = "destinationLookup",
    propertyValue = "jms/OrderQueue")
})
public class Livraison implements MessageListener {
    
    @Inject
    JMSContext jmsContext;
    
    @Resource
    private MessageDrivenContext context;

    @Resource(lookup = "jms/RecepLivraison")
    Destination RecepLivraison;
    
    @Override
    public void onMessage(Message message) {
        try {
            String order = message.getBody(String.class);
            processTicket(order);
        }catch (JMSException jmse) {
            jmse.printStackTrace();
            context.setRollbackOnly();
        }
    }

    private void processTicket(String order) {
        String res = order;
        jmsContext.createProducer().send(RecepLivraison, (Serializable)res);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}