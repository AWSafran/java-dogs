package com.lambdaschool.projectrestdogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ProjectrestdogsApplication
{
    public static final String EXCHANGE_NAME = "LambdaServer";
    public static final String QUEUE_DOGS = "DogsQueue";
    public static final String QUEUE_NEW = "NewDogsQueue";
    public static DogList ourDogList;
    public static void main(String[] args)
    {
        ourDogList = new DogList();
        ApplicationContext ctx = SpringApplication.run(ProjectrestdogsApplication.class, args);
        
        DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
    
    @Bean
    public TopicExchange appExchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }
    
    @Bean
    public Queue appQueueDogs()
    {
        return new Queue(QUEUE_DOGS);
    }
    
    @Bean
    public Binding declareBindingDogs()
    {
        return BindingBuilder.bind(appQueueDogs()).to(appExchange()).with(QUEUE_DOGS);
    }
    
    @Bean Queue appQueueNewDog()
    {
        return new Queue(QUEUE_NEW);
    }
    
    @Bean
    public Binding declareBindingNewDogs()
    {
        return BindingBuilder.bind(appQueueNewDog()).to(appExchange()).with(QUEUE_NEW);
    }
    
    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }
}
