package com.lambdaschool.projectrestdogs.services;

import com.lambdaschool.projectrestdogs.ProjectrestdogsApplication;
import com.lambdaschool.projectrestdogs.models.EndpointMessage;
import com.lambdaschool.projectrestdogs.models.NewDogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener
{
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
    
    @RabbitListener(queues = ProjectrestdogsApplication.QUEUE_DOGS)
    public void receiveDogMessage(EndpointMessage msg)
    {
        System.out.println(msg.toString());
    }
    
    @RabbitListener(queues = ProjectrestdogsApplication.QUEUE_NEW)
    public void receiveNewDog(NewDogMessage msg)
    {
        System.out.println(msg.toString());
    }
}
