package com.g52aim.project.tsp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.StringTokenizer;

import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.TSPInstance;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceReaderInterface;


public class TSPInstanceReader implements TSPInstanceReaderInterface {

    @Override
    public TSPInstanceInterface readTSPInstance(Path path, Random random) {
        // TODO

        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(path);

            // initialise variable for creating TSPInstance
            int numberOfCities = 0;
            Location[] locations = null;

            // read line by line
            String line;
            StringTokenizer st;
            while ((line = reader.readLine()) != "EOF") {
                st = new StringTokenizer(line);
                // scan for dimension
                if (st.nextToken(" : ").equals("DIMENSION")) {
                    numberOfCities = Integer.parseInt(st.nextToken());
                    locations = new Location[numberOfCities];
                    if (numberOfCities == 0) {
                        return null;
                    }
                    break;
                }
            }

            double xCord;
            double yCord;
            int counter = 0;
            String firstSt;
            while (!(line = reader.readLine()).equals("EOF") && counter < numberOfCities) {
                st = new StringTokenizer(line);
                // can i assume?
                // line starts with number
                firstSt = st.nextToken();
                if (isInteger(firstSt)) {
                    // second token parsed
                    xCord = Double.parseDouble(st.nextToken());
                    // third token parsed
                    yCord = Double.parseDouble(st.nextToken());
                    locations[counter] = new Location(xCord, yCord);
                    counter++;
                }
            }
            return new TSPInstance(numberOfCities, locations, random);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return null;
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try {
            Integer.parseInt(s);
            // s is a valid integer
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            // s is not an integer
        }
        return isValidInteger;
    }
}
