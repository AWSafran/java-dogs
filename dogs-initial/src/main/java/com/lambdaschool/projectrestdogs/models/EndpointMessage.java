package com.lambdaschool.projectrestdogs.models;

import java.io.Serializable;

public class EndpointMessage implements Serializable
{
    private String endPoint;
    
    public EndpointMessage()
    {
    
    }
    
    public EndpointMessage(String endPoint)
    {
        this.endPoint = endPoint;
    }
    
    public String getEndPoint()
    {
        return endPoint;
    }
    
    public void setEndPoint(String endPoint)
    {
        this.endPoint = endPoint;
    }
    
    @Override
    public String toString()
    {
        return "MessageDetail{" + endPoint + " was accessed" + '}';
    }
}
