package com.g52aim.project.tsp;

public class benchmarkPerformance {
    private static int[][] ActualOX(int[] p1Array, int[] p2Array, int cutpoint_1, int cutpoint_2) {
        int n = p1Array.length;

        int numberOfPointsWillStay;
        if (cutpoint_2 < cutpoint_1) {
            numberOfPointsWillStay = cutpoint_1;
            cutpoint_1 = cutpoint_2;
            cutpoint_2 = numberOfPointsWillStay;
        }
        numberOfPointsWillStay = cutpoint_2 - cutpoint_1;
        // skip the process when cutpoint1 and cutpoint2 is first and last indices
        if (numberOfPointsWillStay == n - 1) {
            return new int[][]{p2Array, p1Array};
        }

        int[] locationList_p1 = listLocationOfElement(p1Array);
        int[] locationList_p2 = listLocationOfElement(p2Array);

        int ele;
        int pointOfInsertion;
        //  act as temp parent
        int[] child1 = p1Array.clone();
        int[] child2 = p2Array.clone();
        // "nullify" the location index of staying element, so they will not get considered later
        // so there's no duplicate when copy from one to another
        for (pointOfInsertion = 0; pointOfInsertion <= numberOfPointsWillStay; pointOfInsertion++) {
            int locationOfEle;
            // get the element starting from the cutoff point
            ele = child1[cutpoint_1 + pointOfInsertion];
            // get the location of the element in p2
            // find location using city id - 1
            locationOfEle = locationList_p2[ele - 1];
            // set the ele to -1 so will not get copied later
            p2Array[locationOfEle] = -1;

            // do the same for child1
            ele = child2[cutpoint_1 + pointOfInsertion];
            locationOfEle = locationList_p1[ele - 1];
            p1Array[locationOfEle] = -1;
        }
        //  copy to a temp array to rearrange structure
        //  be inclusive
        //  handle the case when cutpoint is at border
        int[] a = new int[n];
        System.arraycopy(p1Array, cutpoint_2 + 1, a, 0, (n - 1) - cutpoint_2);
        System.arraycopy(p1Array, 0, a, (n - 1) - cutpoint_2, cutpoint_2 + 1);

        int[] b = new int[n];
        System.arraycopy(p2Array, cutpoint_2 + 1, b, 0, (n - 1) - cutpoint_2);
        System.arraycopy(p2Array, 0, b, (n - 1) - cutpoint_2, cutpoint_2 + 1);


        //  copy from p1Copy to child2
        //  don't copy if some1[counter] is -1, else retrieve value a[counter]
        int counter = 0;
        for (int i = 0; i < cutpoint_1; i++) {
            ele = a[counter];
            if (ele != -1) {
                child2[i] = ele;
            } else {
                --i;
            }
            counter++;
        }
        for (int i = cutpoint_2 + 1; i < n; i++) {
            ele = a[counter];
            if (ele != -1) {
                child2[i] = ele;
            } else {
                --i;
            }
            counter++;
        }

        //  now copy from p2Copy to child1
        counter = 0;
        for (int i = 0; i < cutpoint_1; i++) {
            ele = b[counter];
            if (ele != -1) {
                child1[i] = ele;
            } else {
                i--;
            }
            counter++;
        }
        for (int i = cutpoint_2 + 1; i < n; i++) {
            ele = b[counter];
            if (ele != -1) {
                child1[i] = ele;
            } else {
                i--;
            }
            counter++;
        }

        return new int[][]{child1, child2};
    }

    private static int[] listLocationOfElement(int[] array) {
        //  index corresponds to the element
        //  the element corresponds to the location (index)
        //  used to find the location of in a less expensive way
        int[] locationList = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            locationList[array[i] - 1] = i;
        }
        return locationList;
    }

    public static void main(String[] args) {
        // int[] array1 = new int[]{1, 2, 3, 4};
        // int[] array2 = new int[]{4, 2, 3, 1};
        //
        // int[][] result = ActualOX(array1, array2, 1, 2);



        System.out.println("print");

    }


}
