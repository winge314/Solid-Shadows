public class Line implements Comparable<Line> {
    private double[] start, end;
    public Line(double[] start, double[] end) {
        this.start = start;
        this.end = end;
    }
    public double[] getStart() {
        return start;
    }
    public double[] getEnd() {
        return end;
    }
    public double distanceFromOrigin() {
        double[] midpoint = new double[] {
            (start[0] + end[0]) / 2,
            (start[1] + end[1]) / 2,
            (start[2] + end[2]) / 2
        };
        return Math.sqrt(Math.pow(midpoint[0], 2) + Math.pow(midpoint[1], 2) + Math.pow(midpoint[2], 2));
    }
    public double distanceFromOrigin(double z) {
        double[] midpoint = new double[] {
            (start[0] + end[0]) / 2,
            (start[1] + end[1]) / 2,
            z
        };
        return Math.sqrt(Math.pow(midpoint[0], 2) + Math.pow(midpoint[1], 2) + Math.pow(midpoint[2], 2));
    }
    public void updatePosition(double[] start, double[] end) {
        this.start = start;
        this.end = end;
    }
    public int compareTo(Line l) {
        double z1 = (start[2] + end[2]) / 2;
        double z2 = (l.getStart()[2] + l.getEnd()[2]) / 2;
        if (z1 > z2) {
            return -1;
        } else if (z1 < z2) {
            return 1;
        } else {
            return 0;
        }
    }
}