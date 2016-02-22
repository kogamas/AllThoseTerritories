package userinterface;

import board.Patch;
import board.Territory;
import board.World;
import board.Reader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import javax.swing.*;


public class InterfaceGui {

    private JFrame mainMap;
    private Polygon poly;
    private Map<Integer, Queue<Polygon>> territoryPolygons;
    private Map<Integer, Territory> territoryMap;
    private int gamePhase;
    private JPanel gamePanel;
    private Polygon endTurnPoly;
    private Polygon endAttPoly;


    public InterfaceGui() {
        initComponents();
    }

    private void initComponents() {

        gamePhase = 0;
        mainMap = new JFrame();
        mainMap.setResizable(false);
        mainMap.setBackground(Color.CYAN);
        mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        territoryPolygons = new HashMap<Integer, Queue<Polygon>>();

        File file = new File("C:\\Users\\Felix\\Dropbox\\Uni\\newuni\\AllThoseTerritories\\angabe\\world.map");
        Reader readWorld = new Reader(file);

        World newWorld = readWorld.getWorld();

        Map<Integer, Patch[]> patches = newWorld.getTerritoryPatchesMap();
        territoryMap = newWorld.getTerritoryMap();


        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                setBackground(Color.CYAN);

                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setColor(Color.GRAY);


                Point[] patchPoints = null;
                int[] x = null;
                int[] y = null;


                super.paintComponent(g);


                int xT1, yT1, xT2, yT2;
                g2d.setStroke(new BasicStroke(3));

                for (Map.Entry<Integer, Territory> iterateTerritoryMap : territoryMap.entrySet()) {

                    for (Integer oneTerr : territoryMap.keySet()) {

                        if (newWorld.isNeighborOf(iterateTerritoryMap.getKey(), oneTerr)) {
                            xT1 = iterateTerritoryMap.getValue().getCapital().x;
                            yT1 = iterateTerritoryMap.getValue().getCapital().y;
                            xT2 = territoryMap.get(oneTerr).getCapital().x;
                            yT2 = territoryMap.get(oneTerr).getCapital().y;

                            if (xT1 > xT2) {
                                xT1 = xT2;
                                xT2 = iterateTerritoryMap.getValue().getCapital().x;
                            }


                            if (xT1 + mainMap.getWidth() - xT2 <= xT2 - xT1) {
                                g2d.drawLine(xT1, yT1, 0, yT1);
                                g2d.drawLine(xT2, yT2, mainMap.getWidth(), yT1);
                            } else {

                                g2d.drawLine(iterateTerritoryMap.getValue().getCapital().x, iterateTerritoryMap.getValue().getCapital().y, territoryMap.get(oneTerr).getCapital().x, territoryMap.get(oneTerr).getCapital().y);

                            }
                        }

                    }

                }

                for (Map.Entry<Integer, Patch[]> iteratePatches : patches.entrySet()) {

                    Queue<Polygon> oneTerritoriesPolygons = new LinkedList<Polygon>();


                    for (int i = 0; i < iteratePatches.getValue().length; i++) {


                        patchPoints = iteratePatches.getValue()[i].getBorders();


                        x = new int[patchPoints.length];
                        y = new int[patchPoints.length];


                        g2d.setStroke(new BasicStroke(4));
                        g2d.setColor(Color.BLACK);


                        for (int j = 0; j < patchPoints.length; j++) {
                            if (j == patchPoints.length - 1) {

                                g2d.drawLine(patchPoints[j].x, patchPoints[j].y, patchPoints[0].x, patchPoints[0].y);

                            } else {
                                g2d.drawLine(patchPoints[j].x, patchPoints[j].y, patchPoints[j + 1].x, patchPoints[j + 1].y);
                            }


                            x[j] = patchPoints[j].x;
                            y[j] = patchPoints[j].y;


                        }


                        g2d.setStroke(new BasicStroke(1));
                        poly = new Polygon(x, y, x.length);

                        int territoryKey=iteratePatches.getKey();
                        Territory currenTerritory= territoryMap.get(territoryKey);

                        if(currenTerritory.getArmy()==0){
                            g2d.setColor(Color.LIGHT_GRAY);
                            g2d.fillPolygon(poly);
                        }

                        if(currenTerritory.getControlledByPlayer()==0){
                            g2d.setColor(Color.RED);
                            g2d.fillPolygon(poly);
                        }

                        if(currenTerritory.getControlledByPlayer()==1){
                            g2d.setColor(Color.BLUE);
                            g2d.fillPolygon(poly);
                        }



                        g2d.setColor(Color.BLACK);


                        if (territoryPolygons != null && territoryPolygons.containsKey(iteratePatches.getKey())) {
                            oneTerritoriesPolygons = territoryPolygons.get(iteratePatches.getKey());
                            oneTerritoriesPolygons.add(poly);

                            territoryPolygons.put(iteratePatches.getKey(), oneTerritoriesPolygons);
                        } else {
                            oneTerritoriesPolygons.add(poly);
                            territoryPolygons.put(iteratePatches.getKey(), oneTerritoriesPolygons);

                        }
                    }
                }

                // mark the capitals of the territories with the amount of armies
                for (Map.Entry<Integer, Territory> iterateTerritoryMap : territoryMap.entrySet()) {



                    g2d.drawString(iterateTerritoryMap.getValue().getArmy()+"", iterateTerritoryMap.getValue().getCapital().x - 5, iterateTerritoryMap.getValue().getCapital().y + 7);
                }


                //draw the EndAttack and EndTurn fields

                g2d.setStroke(new BasicStroke(3));
                g2d.drawRect(900, 600, 125, 35);
                g2d.drawRect(1050, 600, 125, 35);

                Font currentFont = g2d.getFont();

                g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));

                g2d.drawString("End Attack", 910, 627);
                g2d.drawString("End Turn", 1065, 627);

                g2d.setFont(currentFont);

                int[] endAttX = {900, 900, 1025, 1025};
                int[] endAttY = {600, 635, 600, 635};
                int[] endTurnX = {1050, 1050, 1175, 1175};
                int[] endTurnY = {600, 635, 600, 635};

                endAttPoly = new Polygon(endAttX, endAttY, endAttX.length);
                endTurnPoly = new Polygon(endTurnX, endTurnY, endTurnX.length);


            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1250, 650);
            }
        };

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                if (endAttPoly.contains(me.getPoint())) {
                    System.out.println("end Attack button was clicked"); // TODO: call the right function to end The attack
                }

                if (endTurnPoly.contains(me.getPoint())) {
                    System.out.println("end Turn button was clicked"); // TODO: call the right function to end the Turn
                }

                for (Map.Entry<Integer, Queue<Polygon>> q : territoryPolygons.entrySet()) {

                    for (Polygon pol : q.getValue()) {
                        if (pol.contains(me.getPoint())) {
                            if (me.getButton() == MouseEvent.BUTTON1) {

                                System.out.println(q.getKey() + " clicked"); // TODO: call the right function for a leftclick on a territory
                            }

                            if (me.getButton() == MouseEvent.BUTTON3) {
                                System.out.println(q.getKey() + " Right clicked"); // TODO: call the right function for a rightclick on a territory
                                Territory testTerri=territoryMap.get(q.getKey());
                                testTerri.setArmy(5);
                                testTerri.setControlledByPlayer(1);

                                occupyTerritory(q.getKey(), 1);
                            }

                        }
                    }


                }

            }
        };
        gamePanel.addMouseListener(ma);//add listener to panel
        mainMap.add(gamePanel);

        mainMap.pack();
        mainMap.setVisible(true);

    }

    public void occupyTerritory(int territory, int player) {

        gamePanel.repaint();

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGui();
            }
        });
    }
}