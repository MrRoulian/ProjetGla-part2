package facturation;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
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
public class Facturation implements MessageListener {
    
    @Resource
    private MessageDrivenContext context;

    @Override
    public void onMessage(Message message) {
        try {
            Order order = message.getBody(Order.class);
            processTicket(order);
        }catch (JMSException jmse) {
            jmse.printStackTrace();
            context.setRollbackOnly();
        }
    }

    private void processTicket(Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}