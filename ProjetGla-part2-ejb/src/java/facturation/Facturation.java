package facturation;

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
    
    @Inject
    JMSContext jmsContext;
    
    @Resource
    private MessageDrivenContext context;
    
    @Resource(lookup = "jms/RecepFacture")
    Destination RecepFacture;

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
        //System.out.println("On me demande ma facture avec ce message : "+ticket);
        String res = "Commande "+ticket+" facturé";
        jmsContext.createProducer().send(RecepFacture, (Serializable)res);
    }
}