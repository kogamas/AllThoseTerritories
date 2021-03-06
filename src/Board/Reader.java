package Board;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Reader {
    private Map<String, Territory> territoryMap = new HashMap<>();
    private World worldMap = new World();


    public Reader(String[] args) {
        File file = new File("angabe",args[0]);     // Path of .map directory is hardcoded
        this.worldMap.setName(args[0].substring(0,args[0].length()-4));  //adds name of .map file as World name (for display in window)

        createWorld(file);
        worldMap.setAllIds();
    }

    public Reader(File file) {
        createWorld(file);
        worldMap.setAllIds();
    }

    /**
     * This method reads in a File and makes a World object according to the specifications given in the file
     * @param file This is the .map file with the specifications on how to create the World object
     *///todo: maybe simplify this long method and devide it into multibles
    public void createWorld(File file) {
        try {
            BufferedReader bR = new BufferedReader(new FileReader(file));
            String line;
            String[] parts;


            while ((line = bR.readLine()) != null) {
                // System.out.println(line); //for testing


                parts = line.split(" ");
                int i = 0;
                String territoryName = "";
                Point tempPoint;
                Patch tempPatch = new Patch();



                if (parts[i].equals("patch-of")) {
                    i++;

                    while (Character.isLetter(parts[i].charAt(0))) {    //getting the whole territory name
                        territoryName += " " + parts[i];
                        i++;
                    }

                    while (i < parts.length) {
                        tempPatch.addPoint(new Point(Integer.parseInt(parts[i]), Integer.parseInt(parts[i + 1])));
                        i += 2;
                    }

                    if (territoryMap.containsKey(territoryName)) {  //check if there is already a patch for this territory
                        territoryMap.get(territoryName).addPatch(tempPatch);
                    }
                    else {
                        //System.out.println(":::::" + territoryName);      //for testing
                        territoryMap.put(territoryName, new Territory(territoryName));    // if no earlier patch has initialised the territory, do so now
                        territoryMap.get(territoryName).addPatch(tempPatch);
                    }

                } else if (parts[i].equals("capital-of")) {
                    i++;

                    while (Character.isLetter(parts[i].charAt(0))) {    //getting the whole territory name
                        territoryName += " " + parts[i];
                        i++;

                    }

                    tempPoint = new Point(Integer.parseInt(parts[i]), Integer.parseInt(parts[i + 1]));
                    territoryMap.get(territoryName).addCapital(tempPoint);  //setting the Capital of the territory

                } else if (parts[i].equals("neighbors-of")) {
                    i++;
                    String tempTerritoryString = "";
                    Territory tempTerritory;


                    while (!parts[i].equals(":")) {
                        territoryName += " " + parts[i];
                            System.out.println(i + ": " +territoryName);    //for testing
                        i++;

                    }

                    while (i < parts.length - 1) {
                        i++;

                        if (!parts[i].equals("-")) {

                            if (i == parts.length - 1) {
                                tempTerritoryString +=" " + parts[i];
                                tempTerritory = territoryMap.get(tempTerritoryString);

                                //add neighbor to territory
                                territoryMap.get(territoryName).addNeighbor(tempTerritory);

                                //add base territory as a neighbor to neighbor
                                tempTerritory.addNeighbor(territoryMap.get(territoryName));

                                //System.out.println(i + ": " + tempTerritoryString);   //for testing

                            }
                            tempTerritoryString += " " + parts[i];

                        } else {
                            tempTerritory = territoryMap.get(tempTerritoryString);

                            //add neighbor to territory
                            territoryMap.get(territoryName).addNeighbor(tempTerritory);

                            //add base territory as a neighbor to neighbor
                            tempTerritory.addNeighbor(territoryMap.get(territoryName));

                            //System.out.println(i + ": " +tempTerritoryString);  //for testing
                            tempTerritoryString = "";   //reset the string

                        }


                    }
                    //territoryNeighbors.put(territoryName, neighbors);     // i think unnecessery in new implementation
                } else if (parts[i].equals("continent")) {
                    i++;
                    String tempTerritory = "";
                    String continentName = "";
                    Continent tempContinent;
                    while (Character.isLetter(parts[i].charAt(0))) {
                        continentName +=" " + parts[i];
                        //  System.out.println(i + ": " + continentName);    //for testing
                        i++;

                    }
                    int continentBonus = Integer.parseInt(parts[i]);
                    tempContinent = new Continent(continentName,continentBonus);

                    i++;


                    while (i < parts.length - 1) {
                        i++;

                        if (!parts[i].equals("-")) {

                            if (i == parts.length - 1) {
                                tempTerritory += " " + parts[i];
                                tempContinent.addTerritory(territoryMap.get(tempTerritory));
                                //System.out.println(i + ": " +tempTerritory);      //for testing
                            }

                            tempTerritory +=" " + parts[i];

                        } else {
                            tempContinent.addTerritory(territoryMap.get(tempTerritory));
                            // System.out.println(i + ": " +tempTerritory);     //for testing
                            tempTerritory = "";     //reset tempTerritory

                        }


                    }
                    worldMap.addContinent(tempContinent);
                }
            }
            if (worldMap.hasNoContinents()) {
                System.out.println("no continents ------------!!!");
                Continent tempContinent = new Continent("Pangaea",0);
                List<Territory> list = new ArrayList<Territory>(territoryMap.values());
                System.out.println("List size:" + list.size());
                for (int j = 0; j < list.size(); j++) {
                    tempContinent.addTerritory(list.get(j));
                    System.out.println(list.get(j).getName());
                }

                worldMap.addContinent(tempContinent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public World getWorld() {
        return worldMap;
    }

    public static void main(String[] args) {
        /*          //just for testing

        System.out.println("-----creating World and setting ids----");
        Reader world = new Reader(args);
        World board = world.getWorld();

        System.out.println("-----start testing world----");

        System.out.println("----print all territories-----");

        for (Continent c:board.getContinents()) {
            for (Territory t: c.getTerritories()) {
                System.out.println("Id: " + t.getId() + " Name: " + t.getName() + " Army: " + t.getArmy() + " Player: " + t.getControllingPlayerId());
            }
        }
        System.out.println("----Venezuela add army-----");
        board.getArmy(2001);
        board.addArmy(2001, 5);
        System.out.println("----board.getArmy(2001);-----" + board.getArmy(2001));
        System.out.println("board.countTerritories()" + board.countTerritories());
        System.out.println("---------");
        System.out.println("---------");
        System.out.println("---------");
        System.out.println("---------");
        */
    }
}
