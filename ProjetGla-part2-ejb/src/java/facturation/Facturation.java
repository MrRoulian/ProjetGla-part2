package facturation;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
    propertyName = "acknowledgeMode",
    propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(
    propertyName = "destinationType",
    propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(
    propertyName = "destinationLookup",
    propertyValue = "jms/FacturationQueue")
})
public class Facturation implements MessageListener {
    
    @Resource
    private MessageDrivenContext context;

    @Override
    public void onMessage(Message message) {
        try {
            String reveive = message.getBody(String.class);
            processTicket(reveive);
        }catch (JMSException jmse) {
            jmse.printStackTrace();
            context.setRollbackOnly();
        }
    }

    private void processTicket(String ticket) {
        System.out.println("On me demande ma facture avec ce message : "+ticket);
    }
}