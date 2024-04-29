import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Screen extends JPanel implements KeyListener {
    private final double[] rotationCentre = new double[] {0, 0, 5}/*, cameraPos = new double[] {0, 0, 0}*/;
    private final int[] screenDimensions = new int[] {1440, 850};
    private final double slowMulti = 0.05;
    private final double maxDist = 7, brightnessScaling = 2.5; // lower scaling = faster brightness dropoff
    private boolean[] keys = new boolean[7]; // shift, W, S, A, D, Q, E, can be extended to more keys later
    private Line[][] shapes = new Line[5][];
    private int currentShape = 0; // 0 = cube, 1 = tetrahedron, 2 = octahedron, 3 = dodecahedron, 4 = icosahedron
    private Projection p;
    private PolygonBuilder pb = new PolygonBuilder();
    public Screen() {
        addKeyListener(this);
        setFocusable(true);
        p = new Projection(90.0, 1000.0, 0.1, screenDimensions[0] / 2, screenDimensions[1]);
        shapes[0] = pb.constructCube();
        shapes[1] = pb.constructTetrahedron();
        shapes[2] = pb.constructOctahedron();
        shapes[3] = pb.constructDodecahedron();
        shapes[4] = pb.constructIcosahedron();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenDimensions[0], screenDimensions[1]);
        double multi = keys[0] ? slowMulti : 1;
        if (keys[1]) {
            rotateAll(-2 * multi * Math.PI / 180, 0, 0);
        }
        if (keys[2]) {
            rotateAll(2 * multi * Math.PI / 180, 0, 0);
        }
        if (keys[3]) {
            rotateAll(0, -2 * multi * Math.PI / 180, 0);
        }
        if (keys[4]) {
            rotateAll(0, 2 * multi * Math.PI / 180, 0);
        }
        if (keys[5]) {
            rotateAll(0, 0, -2 * multi * Math.PI / 180);
        }
        if (keys[6]) {
            rotateAll(0, 0, 2 * multi * Math.PI / 180);
        }
        sort();
        for (Line l : shapes[currentShape]) {
            drawLine(g, l);
            drawIsometric(g, l);
        }
        g.setColor(Color.GRAY);
        g.fillRect(screenDimensions[0] / 2 - 2, 0, 4, screenDimensions[1]);
        g.setFont(new Font("Cambria", Font.BOLD, 20));
        g.drawString("Perspective", 30, 40);
        g.drawString("Isometric (shadow)", screenDimensions[0] / 2 + 30, 40);
        g.drawString("WASDQE to rotate the shape. Shift to slow down by a lot.", 30, screenDimensions[1] - 60);
        g.drawString("Left and right arrow keys to switch between shapes.", 30, screenDimensions[1] - 30);
    }
    public Dimension getPreferredSize() {
        return new Dimension(screenDimensions[0], screenDimensions[1]);
    }
    public void drawLine(Graphics g, Line l) {
        double[] start = l.getStart();
        double[] end = l.getEnd();
        double[] startProjected = p.project(start);
        double[] endProjected = p.project(end);
        g.setColor(
            new Color((int) (Math.max(Math.min(
                255 * (maxDist - l.distanceFromOrigin())/brightnessScaling, // brute force gg
            255), 0)), 0, 0)
        );
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine((int) startProjected[0], (int) startProjected[1], (int) endProjected[0], (int) endProjected[1]);
    }
    public void drawIsometric(Graphics g, Line l) {
        double[] start = new double[] {l.getStart()[0], l.getStart()[1], 5};
        double[] end = new double[] {l.getEnd()[0], l.getEnd()[1], 5};
        double[] startProjected = p.project(start);
        double[] endProjected = p.project(end);
        g.setColor(
            new Color(0, 0, (int) (Math.max(Math.min(
                255 * (maxDist - l.distanceFromOrigin(5))/brightnessScaling,
            255), 0)))
        );
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine((int) startProjected[0] + screenDimensions[0]/2, (int) startProjected[1], (int) endProjected[0] + screenDimensions[0]/2, (int) endProjected[1]);
    }
    private double sin(double x) {
        return Math.sin(x);
    }
    private double cos(double x) {
        return Math.cos(x);
    }
    public void rotateAll(double x, double y, double z) {
        for (int i = 0; i < shapes[currentShape].length; i++) {
            rotateOne(shapes[currentShape][i], x, y, z);
        }
    }
    public void rotateOne(Line l, double x, double y, double z) {
        y = -y; // Invert y because of the way the screen is oriented (really it's inverting the 2 others)
        double[][] rotationMatrix = new double[][] {
            {cos(y) * cos(z),  sin(x) * sin(y) * cos(z) - cos(x) * sin(z),  cos(x) * sin(y) * cos(z) + sin(x) * sin(z)},
            {cos(y) * sin(z),  sin(x) * sin(y) * sin(z) + cos(x) * cos(z),  cos(x) * sin(y) * sin(z) - sin(x) * cos(z)},
            {-sin(y),          sin(x) * cos(y),                             cos(x) * cos(y)}
        };
        double[] start = new double[] {
            l.getStart()[0] - rotationCentre[0],
            l.getStart()[1] - rotationCentre[1],
            l.getStart()[2] - rotationCentre[2]
        };
        double[] end = new double[] {
            l.getEnd()[0] - rotationCentre[0],
            l.getEnd()[1] - rotationCentre[1],
            l.getEnd()[2] - rotationCentre[2]
        };
        double[] newStart = new double[] {0, 0, 0};
        double[] newEnd = new double[] {0, 0, 0};
        for(int i = 0; i < 3; i++) {
            newStart[0] += rotationMatrix[0][i] * start[i];
            newStart[1] += rotationMatrix[1][i] * start[i];
            newStart[2] += rotationMatrix[2][i] * start[i];
            newEnd[0] += rotationMatrix[0][i] * end[i];
            newEnd[1] += rotationMatrix[1][i] * end[i];
            newEnd[2] += rotationMatrix[2][i] * end[i];
        }
        l.updatePosition(
            new double[] {newStart[0] + rotationCentre[0], newStart[1] + rotationCentre[1], newStart[2] + rotationCentre[2]},
            new double[] {newEnd[0] + rotationCentre[0], newEnd[1] + rotationCentre[1], newEnd[2] + rotationCentre[2]}
        );
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) { // switch case was bugging out earlier
            keys[1] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            keys[2] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            keys[3] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keys[4] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            keys[5] = true;
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            keys[6] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            keys[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape = (currentShape + 1) % shapes.length;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape = (currentShape + shapes.length - 1) % shapes.length;
        }
        repaint();
    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            keys[1] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            keys[2] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            keys[3] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            keys[4] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            keys[5] = false;
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            keys[6] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            keys[0] = false;
        }
        repaint();
    }
    public void keyTyped(KeyEvent e) {}
    public void animate() {
        while(true) {
            repaint();
            try {
                Thread.sleep(1000/60);
            } catch(Exception e) {}
        }
    }
    private void sort() {
        Line[] shape = shapes[currentShape];
        sort(shape, 0, shape.length - 1);
    }
    private void sort(Line[] shape, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(shape, l, m);
            sort(shape, m + 1, r);
            merge(shape, l, m, r);
        }
    }
    private void merge(Line[] shape, int l, int m, int r) {
        Line[] left = new Line[m - l + 1];
        Line[] right = new Line[r - m];
        for (int i = 0; i < left.length; i++) {
            left[i] = shape[l + i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = shape[m + 1 + i];
        }
        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) <= 0) {
                shape[k] = left[i];
                i++;
            } else {
                shape[k] = right[j];
                j++;
            }
            k++;
        }
        while (i < left.length) {
            shape[k] = left[i];
            i++;
            k++;
        }
        while (j < right.length) {
            shape[k] = right[j];
            j++;
            k++;
        }
    }
}