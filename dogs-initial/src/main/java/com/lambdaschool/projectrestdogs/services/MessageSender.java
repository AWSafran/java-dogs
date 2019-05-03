package com.lambdaschool.projectrestdogs.services;

import com.lambdaschool.projectrestdogs.ProjectrestdogsApplication;
import com.lambdaschool.projectrestdogs.models.Dog;
import com.lambdaschool.projectrestdogs.models.EndpointMessage;
import com.lambdaschool.projectrestdogs.models.NewDogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender
{
    private RabbitTemplate rt;
    
    public MessageSender(RabbitTemplate rt)
    {
        this.rt = rt;
    }
    
    public void sendEndpointMessage(String endpoint)
    {
        EndpointMessage endpointMessage = new EndpointMessage(endpoint);
        rt.convertAndSend(ProjectrestdogsApplication.QUEUE_DOGS, endpointMessage);
    }
    
    public void sendNewDogMessage(Dog newDog)
    {
        NewDogMessage dogMsg = new NewDogMessage(newDog);
        rt.convertAndSend(ProjectrestdogsApplication.QUEUE_NEW, dogMsg);
    }
}
