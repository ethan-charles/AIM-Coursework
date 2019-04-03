package com.g52aim.project.tsp;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import com.g52aim.project.tsp.instance.Location;

public class SolutionPrinter {

    public SolutionPrinter() {

    }

    /**
     * @param routeLocations The array of Locations ordered in route order.
     */
    public static void printSolution(Location[] routeLocations) {

        String route = Arrays.stream(routeLocations).map(l -> "( " + l.getX() + ", " + l.getY() + " )").collect(Collectors.joining(" -> "));
        System.out.println(route);
    }

    /**
     * @param routeLocations The arraylist of Locations ordered in route order.
     */
    public static void printSolution(ArrayList<Location> routeLocations) {

        String route = routeLocations.stream().map(l -> "( " + l.getX() + ", " + l.getY() + " )").collect(Collectors.joining(" -> "));
        System.out.println(route);
    }
}