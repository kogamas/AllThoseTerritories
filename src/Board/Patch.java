package Board;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

class Patch {
    private List<Point> borders = new LinkedList<>();

    Patch() {}

    /**
     * This method adds a Point to the patch of land
     * @param p This is the Point added (java.awt.Point)
     */
    void addPoint(Point p) {
        this.borders.add(p);
    }

    /**
     *
     * @return the borders of a Patch as an Array of Points (java.awt.Point)
     */
    Point[] getBorders() {
        return borders.toArray(new Point[borders.size()]);
    }
}
