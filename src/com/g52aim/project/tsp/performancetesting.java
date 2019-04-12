package com.g52aim.project.tsp;

public class performancetesting {
    private static int[] listLocationOfElement(int[] array) {
        // index corresponds to the element
        // the element corresponds to the location (index)
        // used to find the location of in a less expensive way
        int[] locationList = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            locationList[array[i]] = i;
        }
        return locationList;
    }

    private static void OX(int[] array) {
        int[] p1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] p2 = new int[]{3, 4, 1, 0, 7, 6, 5, 8, 2};


        int[] p1Copy = p1.clone();
        int[] p2Copy = p2.clone();


        int cutpoint_1 = 3;
        int cutpoint_2 = 7;

        int numberOfPointsWillStay;
        if (cutpoint_2 < cutpoint_1) {
            numberOfPointsWillStay = cutpoint_1;
            cutpoint_1 = cutpoint_2;
            cutpoint_2 = numberOfPointsWillStay;
        }

        numberOfPointsWillStay = cutpoint_2 - cutpoint_1;
        int[] locationList_p1 = listLocationOfElement(p1);
        int[] locationList_p2 = listLocationOfElement(p2Copy);

        int ele;
        int pointOfInsertion;

        // "nullify" the location index of staying element, so they will not get considered later
        // so there's no duplicate when copy from one to another
        for (pointOfInsertion = 0; pointOfInsertion < numberOfPointsWillStay; pointOfInsertion++) {
            int locationOfEle;

            // get the element starting from the cutoff point
            ele = p1[cutpoint_1 + pointOfInsertion];
            System.out.println(ele);
            // get the location of the element in p2
            locationOfEle = locationList_p2[ele];
            // set the ele to -1 so will not get copied later
            p2Copy[locationOfEle] = -1;

            // do the same for child1
            ele = p2[cutpoint_1 + pointOfInsertion];
            locationOfEle = locationList_p1[ele];
            p1Copy[locationOfEle] = -1;
        }
        int n = p1.length;
        int[] a = new int[n];
        System.arraycopy(p1Copy, cutpoint_2, a, 0, n - cutpoint_2);
        System.arraycopy(p1Copy, 0, a, n - cutpoint_2, cutpoint_2);
        p1Copy = a;
        int[] b = new int[n];
        System.arraycopy(p2Copy, cutpoint_2, b, 0, n - cutpoint_2);
        System.arraycopy(p2Copy, 0, b, n - cutpoint_2, cutpoint_2);
        p2Copy = b;

        int[] child1 = p1.clone();
        int[] child2 = p2.clone();

        // copy from p1Copy to child2
        int counter = 0;
        for (int i = cutpoint_2; i < n; i++) {
            ele = p1Copy[counter];
            if (ele != -1) {
                child2[i] = ele;
            } else {
                --i;
            }
            counter++;
        }
        for (int i = 0; i < cutpoint_1; i++) {
            ele = p1Copy[counter];
            if (ele != -1) {
                child2[i] = ele;
            } else {
                --i;
            }
            counter++;
        }


        // now copy from p2Copy to child1
        counter = 0;
        for (int i = cutpoint_2; i < n; i++) {
            ele = p2Copy[counter];
            if (ele != -1) {
                child1[i] = ele;
            } else {
                --i;
            }
            counter++;
        }
        for (int i = 0; i < cutpoint_1; i++) {
            ele = p2Copy[counter];
            if (ele != -1) {
                child1[i] = ele;
            } else {
                --i;
            }
            counter++;
        }
    }

    public static void defaultOX() {
        int[] p1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] p22 = new int[]{3, 4, 1, 0, 7, 6, 5, 8, 2};
        if (p1.length <= 1 || p22.length <= 1 || p1.length != p22.length) {
            System.out.println("Error in ox (order crossover) input permutation are not of the same length or one of them is of size <= 1");
            System.exit(0);
        }

        int[] p2 = (int[]) p22.clone();
        int n = p1.length;
        int point1 = 7;

        int point2 = 3;

        int pointsToCopy;
        if (point2 < point1) {
            pointsToCopy = point1;
            point1 = point2;
            point2 = pointsToCopy;
        }

        pointsToCopy = point2 - point1;
        int[] inverseP2 = listLocationOfElement(p2);

        int job;
        int insertionPoint;
        for (insertionPoint = 0; insertionPoint < pointsToCopy; ++insertionPoint) {
            job = p1[point1 + insertionPoint];
            int job_index = inverseP2[job];
            p2[job_index] = -1;
        }

        insertionPoint = inverseP2[p1[point1]];
        int[] receiver = new int[n + pointsToCopy];

        int j;
        for (int i = 0; i <= n; ++i) {
            if (i < insertionPoint) {
                receiver[i] = p2[i];
            }

            if (i == insertionPoint) {
                for (j = 0; j < pointsToCopy; ++j) {
                    receiver[i + j] = p1[point1 + j];
                }
            }

            if (i > insertionPoint) {
                receiver[i - 1 + pointsToCopy] = p2[i - 1];
            }
        }

        int[] p3 = new int[n];
        int counter = 0;

        for (j = 0; j < n; ++j) {
            job = receiver[counter];
            if (job == -1) {
                ++counter;
                --j;
            } else {
                p3[j] = job;
                ++counter;
            }
        }
    }

    public static void main(String[] args) {
        int[] p1Array = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] p2Array = new int[]{3, 4, 1, 0, 7, 6, 5, 8, 2};
        int[] p1copy = p1Array.clone();
        int[] p2copy = p2Array.clone();

        int n = p1Array.length;

        int cutpoint_1 = 3;
        int cutpoint_2 = 7;

        int numberOfPointsWillStay;
        if (cutpoint_2 < cutpoint_1) {
            numberOfPointsWillStay = cutpoint_1;
            cutpoint_1 = cutpoint_2;
            cutpoint_2 = numberOfPointsWillStay;
        }
        numberOfPointsWillStay = cutpoint_2 - cutpoint_1;
        int[] locationList_p1 = listLocationOfElement(p1Array);
        int[] locationList_p2 = listLocationOfElement(p2Array);
        // create mapping
        int[] mapP1 = new int[n];
        int[] mapP2 = new int[n];
        for (int i = 0; i < cutpoint_1; i++) {
            mapP1[p1Array[i]] = -1;
            mapP2[p2Array[i]] = -1;
        }
        for (int i = cutpoint_1; i < cutpoint_2; i++) {
            mapP1[p1Array[i]] = p2Array[i];
            mapP2[p2Array[i]] = p1Array[i];
        }
        for (int i = cutpoint_2; i < n; i++) {
            mapP1[p1Array[i]] = -1;
            mapP2[p2Array[i]] = -1;
        }
        //find the mapping by

        // copy from p2 into p1
        for (int i = 0; i < cutpoint_1; i++) {
            int temp = mapP1[p2copy[i]];
            if (temp == -1) {
                p1Array[i] = p2copy[i];
            } else {
                p1Array[i] = temp;
            }
            temp = mapP2[p1copy[i]];
            if (temp == -1) {
                p2Array[i] = p1copy[i];
            } else {
                p2Array[i] = temp;
            }
        }

        for (int i = cutpoint_2; i < n; i++) {
            int temp = mapP1[p2copy[i]];
            if (temp == -1) {
                p1Array[i] = p2copy[i];
            } else {
                p1Array[i] = temp;
            }
            temp = mapP2[p1copy[i]];
            if (temp == -1) {
                p2Array[i] = p1copy[i];
            } else {
                p2Array[i] = temp;
            }
        }


        System.out.println("print");

    }


}
