package com.lambdaschool.projectrestdogs.models;

import java.io.Serializable;

public class NewDogMessage implements Serializable
{
    private String breed;
    private long id;
    
    public NewDogMessage()
    {}
    
    public NewDogMessage(Dog dog)
    {
        this.breed = dog.getBreed();
        this.id = dog.getId();
    }
    
    public String getBreed()
    {
        return breed;
    }
    
    public long getId()
    {
        return id;
    }
    
    @Override
    public String toString()
    {
        return "NewDogMessage{" +
                "breed='" + breed + '\'' +
                ", id=" + id +
                '}';
    }
}
