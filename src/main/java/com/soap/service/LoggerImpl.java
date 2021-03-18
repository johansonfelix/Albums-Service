package com.soap.service;

import DAO.DatabaseManager;
import com.Albums;
import exceptions.RepException;
import pojo.LogEntry;

import javax.jws.WebService;
import java.util.ArrayList;

@WebService(name ="Logger", endpointInterface = "com.soap.service.Logger")
public class LoggerImpl implements Logger {

    Albums albums = Albums.getAlbumsInstance();

    @Override
    public ArrayList<LogEntry> getChangeLogs() throws RepException {

        ArrayList<LogEntry> logs = albums.getLogs();
        if(logs.size()==0) {
            System.out.println("REPEXCEPTION: NO LOG ENTRIES.");
            throw new RepException("REPEXCEPTION: NO LOG ENTRIES.");

        }
        else {
            System.out.println("Returning Change Logs...");
            return logs;
        }
    }

    @Override
    public void clearLogs() throws RepException{ System.out.println("REPEXCEPTION: The Method is not yet supported.");throw new RepException("The Method is not yet supported.");}
}
