import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Patch {
    private ArrayList<Point> borders = new ArrayList<>();

    public Patch(Point[] points) {
        for (Point p: points) {
            borders.add(p);
            List<Point> newList = Arrays.asList(points);
            this.borders.addAll(newList);
        }
    }
    public Patch() {}

    void addPoint(Point p) {
        this.borders.add(p);
    }
}
