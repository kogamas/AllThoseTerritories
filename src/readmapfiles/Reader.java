package readmapfiles;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Felix on 08.01.2016.
 */
public class Reader {
    private Map<String, Queue<Point>> territoryPatches = null;
    private Map<String, Point> territoryCapital = null;
    private Map<String, Queue<String>> territoryNeighbors = null;
    private Map<String, Queue<String>> territoryContinent = null;
    private Map<String, Integer> continentBoni = null;

    public Map<String, Queue<Point>> getTerritoryPatches() {
        return territoryPatches;
    }

    public Map<String, Point> getTerritoryCapital() {
        return territoryCapital;
    }

    public Map<String, Queue<String>> getTerritoryNeighbors() {
        return territoryNeighbors;
    }

    public Map<String, Queue<String>> getTerritoryContinent() {
        return territoryContinent;
    }

    public Map<String, Integer> getContinentBoni() {
        return continentBoni;
    }

    public Reader(File file) {

        //File file = new File("C:\\Users\\Felix\\Desktop\\Uni\\PK\\Abschlussaufgabe\\mapDateien\\africa.map");
        Map<String, Queue<Point>> territoryPatches = new HashMap<>();
        Map<String, Point> territoryCapital = new HashMap<>();
        Map<String, Queue<String>> territoryNeighbors = new HashMap<>();
        Map<String, Queue<String>> territoryContinent = new HashMap<>();
        Map<String, Integer> continentBoni = new HashMap<>();


        try {
            BufferedReader bR = new BufferedReader(new FileReader(file));
            String line;
            String[] parts;


            while ((line = bR.readLine()) != null) {
                System.out.println(line);


                Queue<Point> coordinates = new LinkedList<>();
                Queue<String> neighbors = new LinkedList<>();
                parts = line.split(" ");
                int i = 0;
                String territoryName = "";
                Point onePoint;


                if (parts[i].equals("patch-of")) {

                    i++;


                    while (Character.isLetter(parts[i].charAt(0))) {
                        territoryName += parts[i];
                        i++;

                    }

                    while (i < parts.length) {

                        onePoint = new Point(Integer.parseInt(parts[i]), Integer.parseInt(parts[i + 1]));


                        coordinates.add(onePoint);
                        i += 2;
                    }
                    territoryPatches.put(territoryName, coordinates);
                } else if (parts[i].equals("capital-of")) {
                    i++;

                    while (Character.isLetter(parts[i].charAt(0))) {
                        territoryName += parts[i];
                        i++;

                    }

                    onePoint = new Point(Integer.parseInt(parts[i]), Integer.parseInt(parts[i + 1]));

                    territoryCapital.put(territoryName, onePoint);
                } else if (parts[i].equals("neighbors-of")) {
                    i++;
                    String oneTerritory = "";


                    while (!parts[i].equals(":")) {
                        territoryName += parts[i];
                        System.out.println(territoryName);
                        i++;

                    }

                    while (i < parts.length - 1) {
                        i++;

                        if (!parts[i].equals("-")) {

                            if (i == parts.length - 1) {
                                oneTerritory += parts[i];
                                neighbors.add(oneTerritory);
                                System.out.println(oneTerritory);


                            }

                            oneTerritory += parts[i];

                        } else {
                            neighbors.add(oneTerritory);
                            System.out.println(oneTerritory);
                            oneTerritory = "";

                        }


                    }
                    territoryNeighbors.put(territoryName, neighbors);
                } else if (parts[i].equals("continent")) {
                    i++;
                    String oneTerritory = "";

                    while (Character.isLetter(parts[i].charAt(0))) {
                        territoryName += parts[i];
                        System.out.println(territoryName);
                        i++;

                    }

                    continentBoni.put(territoryName, Integer.parseInt(parts[i]));
                    i++;


                    while (i < parts.length - 1) {
                        i++;

                        if (!parts[i].equals("-")) {

                            if (i == parts.length - 1) {
                                oneTerritory += parts[i];
                                neighbors.add(oneTerritory);
                                System.out.println(oneTerritory);


                            }

                            oneTerritory += parts[i];

                        } else {
                            neighbors.add(oneTerritory);
                            System.out.println(oneTerritory);
                            oneTerritory = "";

                        }


                    }
                    territoryContinent.put(territoryName, neighbors);
                }


            }

            this.territoryCapital = territoryCapital;
            this.territoryNeighbors = territoryNeighbors;
            this.territoryPatches = territoryPatches;
            this.territoryContinent = territoryContinent;
            this.continentBoni = continentBoni;

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {

        File file = new File("C:\\Users\\Felix\\Desktop\\Uni\\PK\\Abschlussaufgabe\\mapDateien\\world.map");
        Reader world = new Reader(file);

        String str;
        Queue<String> queue;
        int in;

        in = world.continentBoni.get("Australia");
        System.out.println(in);


        queue = world.territoryNeighbors.get("Alaska");
        System.out.print(queue);


    }


}
