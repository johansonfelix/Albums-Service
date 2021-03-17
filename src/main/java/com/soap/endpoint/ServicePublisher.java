package com.soap.endpoint;

import com.soap.service.LoggerImpl;
import javax.xml.ws.Endpoint;

public class ServicePublisher {
    public static void main(String[] args){
        Endpoint.publish("http://localhost:8088/albums/LoggerService/Logger", new LoggerImpl());

        System.out.println("Server is running");
    }
}
