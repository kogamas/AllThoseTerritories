package userinterface;

import game.Game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;


public class InterfaceGui {

    private JFrame mainMap;
    private Polygon poly;
    private Map<Integer, Queue<Polygon>> territoryPolygons;
    public Game game;
    private int gamePhase;
    private JPanel gamePanel;
    private Polygon endTurnPoly;
    private Polygon endAttPoly;
    private boolean readyToClick;
    private int markedTerritory;
    private int aimedTerritory;


    public InterfaceGui(Game game) {
        this.game = game;
        initComponents();
    }

    private void initComponents() {

        gamePhase = 0;
        mainMap = new JFrame();
        mainMap.setResizable(false);
        mainMap.setBackground(Color.CYAN);
        mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        territoryPolygons = new HashMap<Integer, Queue<Polygon>>();
        markedTerritory = -1;
        aimedTerritory = -1;


        Map<Integer, List<Point[]>> patches = game.getTerritoryPointArrayMap();


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

                Map<Integer, int[]> neighborList = game.getNeighborsList();

                for (Map.Entry<Integer, int[]> iterateNeigborList : neighborList.entrySet()) {

                    xT1 = game.getCapital(iterateNeigborList.getKey()).x;
                    yT1 = game.getCapital(iterateNeigborList.getKey()).y;

                    for (int i = 0; i < iterateNeigborList.getValue().length - 1; i++) {

                        xT2 = game.getCapital(iterateNeigborList.getValue()[i]).x;
                        yT2 = game.getCapital(iterateNeigborList.getValue()[i]).y;

                        if (xT1 > xT2) {
                            xT1 = xT2;
                            xT2 = game.getCapital(iterateNeigborList.getKey()).x;
                        }


                        if (xT1 + mainMap.getWidth() - xT2 <= xT2 - xT1) {
                            g2d.drawLine(xT1, yT1, 0, yT1);
                            g2d.drawLine(xT2, yT2, mainMap.getWidth(), yT1);
                        } else {

                            g2d.drawLine(game.getCapital(iterateNeigborList.getKey()).x, game.getCapital(iterateNeigborList.getKey()).y, game.getCapital(iterateNeigborList.getValue()[i]).x, game.getCapital(iterateNeigborList.getValue()[i]).y);

                        }
                    }
                }


                for (Map.Entry<Integer, List<Point[]>> iteratePatches : patches.entrySet())

                {

                    Queue<Polygon> oneTerritoriesPolygons = new LinkedList<Polygon>();


                    for (Point[] onePatchPoints : iteratePatches.getValue()) {


                        patchPoints = onePatchPoints;


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

                        int territoryKey = iteratePatches.getKey();


                        if (game.getControllingPlayerId(territoryKey) < 0) {
                            g2d.setColor(Color.LIGHT_GRAY);
                            g2d.fillPolygon(poly);
                        }

                        if (game.getControllingPlayerId(territoryKey) == 2) {
                            g2d.setColor(Color.RED);
                            g2d.fillPolygon(poly);
                        }

                        if (game.getControllingPlayerId(territoryKey) == 1) {
                            g2d.setColor(Color.BLUE);
                            g2d.fillPolygon(poly);
                        }

                        if (territoryKey == markedTerritory) {
                            g2d.setColor(Color.yellow);
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

                    // mark the capitals of the territories with the amount of armies
                    g2d.drawString(game.getArmy(iteratePatches.getKey()) + "", game.getCapital(iteratePatches.getKey()).x - 5, game.getCapital(iteratePatches.getKey()).y + 7);

                }


                //draw the EndAttack and EndTurn fields

                g2d.setStroke(new

                                BasicStroke(3)

                );
                g2d.drawRect(900, 600, 125, 35);
                g2d.drawRect(1050, 600, 125, 35);

                Font currentFont = g2d.getFont();

                g2d.setFont(new

                                Font("TimesRoman", Font.PLAIN, 20)

                );

                g2d.drawString("End Attack", 910, 627);
                g2d.drawString("End Turn", 1065, 627);

                g2d.setFont(currentFont);

                int[] endAttX = {900, 900, 1025, 1025};
                int[] endAttY = {600, 635, 600, 635};
                int[] endTurnX = {1050, 1050, 1175, 1175};
                int[] endTurnY = {600, 635, 600, 635};

                endAttPoly = new

                        Polygon(endAttX, endAttY, endAttX.length);

                endTurnPoly = new

                        Polygon(endTurnX, endTurnY, endTurnX.length);


            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1250, 650);
            }
        };


        readyToClick = true;

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);


                if (endAttPoly.contains(me.getPoint()) && readyToClick == true ) {
                    readyToClick = false;
                    System.out.println("end Attack button was clicked"); // just for testing

                    gamePhase = 4;
                    game.nextPhase();

                } else if (endTurnPoly.contains(me.getPoint()) && readyToClick == true) {
                    readyToClick = false;
                    System.out.println("end Turn button was clicked"); // just for testing

                    gamePhase = 1;
                    game.nextRound();
                } else {
                    for (Map.Entry<Integer, Queue<Polygon>> q : territoryPolygons.entrySet()) {

                        for (Polygon pol : q.getValue()) {
                            if (pol.contains(me.getPoint())) {

                                boolean isNeighborOF = false;

                                for(Integer neighbor:game.getNeighborsList().get(markedTerritory)){
                                    if(q.getKey()==neighbor)
                                        isNeighborOF=true;
                                }

                                if (me.getButton() == MouseEvent.BUTTON1 && readyToClick == true) {
                                    readyToClick = false;
                                    System.out.println(q.getKey() + " clicked"); // TODO: call the right function for a leftclick on a territory

                                    if (gamePhase == 0) {

                                        if (!game.claimTerritory(1, q.getKey())) {
                                            gamePhase = 2;
                                            game.nextPhase();

                                        }

                                    } else if (gamePhase == 1) {

                                        game.moveReinforcement(1, q.getKey(), game.showReinforcement(1));
                                        game.nextPhase();
                                        gamePhase = 2;


                                    } else if (gamePhase == 2) {

                                        if (markedTerritory < 0) {
                                            markedTerritory = q.getKey();
                                        } else if (game.getControllingPlayerId(q.getKey()) == 1) {
                                            markedTerritory = q.getKey();
                                        }

                                        else if (!isNeighborOF){
                                            // do nothing if its a enemy territorium but not a neighbor
                                        }

                                        else{
                                            if(game.attack(markedTerritory, q.getKey())) {
                                                gamePhase = 3;
                                                aimedTerritory = q.getKey();
                                            }
                                        }

                                    } else if (gamePhase == 3) {

                                        if(q.getKey()==aimedTerritory){
                                            game.moveArmy(markedTerritory,aimedTerritory,1);
                                        }
                                        else{
                                            aimedTerritory=-1;
                                            markedTerritory=-1;
                                            gamePhase=2;
                                        }

                                    }

                                    else if(gamePhase==4){
                                        if(markedTerritory<0 || aimedTerritory<0){
                                            markedTerritory=q.getKey();
                                        }

                                    }


                                }

                                if (me.getButton() == MouseEvent.BUTTON3 && readyToClick == true && gamePhase == 2) {
                                    readyToClick = false;
                                    System.out.println(q.getKey() + " Right clicked"); // TODO: call the right function for a rightclick on a territory
                                    if(gamePhase==4){
                                        if(aimedTerritory<0 && isNeighborOF) {
                                            aimedTerritory =q.getKey();
                                            game.moveArmy(markedTerritory,aimedTerritory,1);
                                        }

                                        if(q.getKey()==aimedTerritory){
                                            game.moveArmy(markedTerritory,aimedTerritory,1);
                                        }
                                        if(q.getKey()==markedTerritory){
                                            game.moveArmy(aimedTerritory,markedTerritory,1);
                                        }
                                    }
                                }

                            }
                        }


                    }
                }

                gamePanel.repaint();
                readyToClick = true;

            }
        };
        gamePanel.addMouseListener(ma);//add listener to panel
        mainMap.add(gamePanel);

        mainMap.pack();
        mainMap.setVisible(true);

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new InterfaceGui();
            }
        });
    }
}