package com.example.scorpiowg.a2340project.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the CVS File for the shelter information.
 * @author Ruinan Wang
 * @version 1.8
 * created on 02/25/2018
 */
public class CSVFile {
    private final InputStream inputStream;

    /**
     * Provide the input
     * @param inputStream the current input stream
     */
    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    /**
     * Generate and return a map of filtered shelters
     * @return a Map of filtered shelters
     */
    public Map<String, String[]> read(){
        Map shelterMap = new HashMap();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String oneLine;
            reader.readLine();
            //noinspection NestedAssignment
            while ((oneLine = reader.readLine()) != null) {
                String[] row = oneLine.split(",");
                String address = row[6] + "," + row[7] + "," + row[8];
                String specialNotes = "";
                for (int i = 9; i < (row.length - 2); i++) {
                    specialNotes =  specialNotes + row[i] + ",";
                }
                specialNotes += row[row.length - 2];
                String[] modifiedRow = new String[8];
                for (int i = 1; i < 6; i++) {
                    modifiedRow[i-1] = row[i];
                }
                modifiedRow[5] = address;
                modifiedRow[6] = specialNotes;
                modifiedRow[7] = row[row.length - 1];
                String uniqueid = row[0];
                shelterMap.put(uniqueid,modifiedRow);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
//        finally {
//            try {
//                inputStream.close();
//            }
//            catch (IOException e) {
//                throw new RuntimeException("Error while closing input stream: "+ e);
//            }
//        }
        return shelterMap;
    }
}