package com.soap.service;

import exceptions.RepException;
import pojo.LogEntry;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService
public interface Logger {

    @WebMethod(operationName = "getChangeLogs")
    ArrayList<LogEntry> getChangeLogs() throws RepException;

    @WebMethod(operationName = "clearLogs")
    void clearLogs() throws RepException;
}
