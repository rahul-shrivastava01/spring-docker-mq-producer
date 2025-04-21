package com.local.spring_docker_mq_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {

    private final JmsTemplate jmsTemplate;


    @Autowired
    public AppController(final JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/home")
    public String homeMessage(){
        return "Hi Jms!";
    }

    @GetMapping("/send-message")
    public String sendMessageToQueue(){
        try{
            System.out.println("📤 Sending test message to DEV.QUEUE.1...");
            jmsTemplate.convertAndSend("Hello Baby JMS!");
            System.out.println("✅ Message sent!");
        } catch(Exception e){
            e.printStackTrace();
        }
        return "Message sent";
    }

}
