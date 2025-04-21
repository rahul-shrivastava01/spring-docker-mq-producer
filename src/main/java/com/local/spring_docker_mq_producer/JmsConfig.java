package com.local.spring_docker_mq_producer;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import com.ibm.msg.client.jakarta.wmq.compat.jms.internal.JMSC;
import jakarta.annotation.PostConstruct;
import jakarta.jms.JMSException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setDefaultDestinationName("DEV.QUEUE.1"); // Queue name
        return jmsTemplate;
    }

    @Bean
    public MQConnectionFactory connectionFactory() throws JMSException {
        MQConnectionFactory connectionFactory = new MQConnectionFactory();
        connectionFactory.setHostName("mq"); // Instead of localhost/127.0.0.1
        connectionFactory.setPort(1414);
        connectionFactory.setQueueManager("QM1");
        connectionFactory.setChannel("DEV.APP.SVRCONN"); // This will always be DEV.APP.XX
        connectionFactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
        connectionFactory.setStringProperty(WMQConstants.USERID, "app"); // For local application use app, admin won't work here with DEV.APP.SVRCONN
        connectionFactory.setStringProperty(WMQConstants.PASSWORD, "passw0rd");
        System.out.println("🔍 MQConnectionFactory class: " + connectionFactory.getClass().getName());
        return connectionFactory;
    }

    /**
     * This will ensure that our connection factory is set and used rather than some default one.
     * @param connectionFactory
     * @return
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(MQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    /**
     *
     */
    @PostConstruct
    public void postConstruct() {
        System.out.println("✅ MQ Config is being used!");
    }

}
