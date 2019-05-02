package com.lambdaschool.projectrestdogs.controller;

import com.lambdaschool.projectrestdogs.exception.ResourceNotFoundException;
import com.lambdaschool.projectrestdogs.models.Dog;
import com.lambdaschool.projectrestdogs.ProjectrestdogsApplication;
import com.lambdaschool.projectrestdogs.models.EndpointMessage;
import com.lambdaschool.projectrestdogs.models.ErrorDetail;
import com.lambdaschool.projectrestdogs.services.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.Endpoint;
import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    @Autowired
    MessageSender msgSender;
    
    private static final Logger logger = LoggerFactory.getLogger(DogController.class);
    private final RabbitTemplate rt;
    
    public DogController(RabbitTemplate rt)
    {
        this.rt = rt;

    }
    
    // localhost:8080/dogs/dogs
    @GetMapping(value = "/dogs", produces = {"application/json"})
    public ResponseEntity<?> getAllDogs()
    {
        //Messages for all dogs created here, will loop through existing dogs initially
    
        for(Dog d : ProjectrestdogsApplication.ourDogList.dogList)
        {
            msgSender.sendNewDogMessage(d);
        }
        System.out.println("Made it to get mapping");
        msgSender.sendEndpointMessage("/dogs/dogs");
        logger.info("/dogs/dogs has been accessed");
        return new ResponseEntity<>(ProjectrestdogsApplication.ourDogList.dogList, HttpStatus.OK);
    }

    // localhost:8080/dogs/{id}
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> getDogDetail(@PathVariable long id)
    {
        msgSender.sendEndpointMessage("/dogs/" + id);
        logger.info("/dogs/" + id + " has been accessed");
        Dog rtnDog;
        if(ProjectrestdogsApplication.ourDogList.findDog(d -> (d.getId() == id)) == null)
        {
            throw new ResourceNotFoundException("Dog with id " + id + " not found");
        } else
        {
            rtnDog = ProjectrestdogsApplication.ourDogList.findDog(d -> (d.getId() == id));
        }
        return new ResponseEntity<>(rtnDog, HttpStatus.OK);
    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}", produces = {"application/json"})
    public ResponseEntity<?> getDogBreeds (@PathVariable String breed)
    {
        msgSender.sendEndpointMessage("/dogs/breeds/" + breed);
        logger.info("/dogs/breeds/" + breed + " has been accessed");
        ArrayList<Dog> rtnDogs = ProjectrestdogsApplication.ourDogList.
                findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));
        if(rtnDogs.size() == 0)
        {
            throw new ResourceNotFoundException("Dog with breed " + breed + " not found");
        } else
        {
            return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
        }
    }
    
    //localhost:2020/dogs/table
    @GetMapping(value="/table")
    public ModelAndView displayDogTable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");
        mav.addObject("dogList", ProjectrestdogsApplication.ourDogList.dogList);
        
        return mav;
    }
    
    @GetMapping(value="/apartment")
    public ModelAndView displayApartmentDogs()
    {
        ArrayList<Dog> apartmentDogs = new ArrayList<>();
        
        apartmentDogs = ProjectrestdogsApplication.ourDogList.findDogs(d -> d.isApartmentSuitable());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");
        mav.addObject("dogList", apartmentDogs);
        
        return mav;
    }
}
