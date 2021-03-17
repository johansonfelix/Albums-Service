package com.soap.service;

import DAO.DatabaseManager;
import exceptions.RepException;
import pojo.LogEntry;

import javax.jws.WebService;
import java.util.ArrayList;

@WebService(name ="Logger", endpointInterface = "com.soap.service.Logger")
public class LoggerImpl implements Logger {

    DatabaseManager db = DatabaseManager.getInstance();

    @Override
    public ArrayList<LogEntry> getChangeLogs() throws RepException {

        ArrayList<LogEntry> logs = db.getAllLogEntries();
        if(logs.size()==0) {
            System.out.println("REPEXCEPTION: NO LOG ENTRIES.");
            throw new RepException();
            //throw new com.soap.service.RepException("NO LOG ENTRIES");
        }
        else {
            System.out.println("Returning Change Logs...");
            return db.getAllLogEntries();
        }
    }

    @Override
    public void clearLogs() throws RepException{ throw new RepException("The Method is not yet supported.");}
}
