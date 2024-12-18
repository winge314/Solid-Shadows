public class PolygonBuilder {
    private final double phi = (1 + Math.sqrt(5)) / 2, invPhi = 1 / phi;
    public Line[] newCube() {
        Line[] shape = new Line[12];
        
        double[] p1 = new double[] {1, 1, 6};
        double[] p2 = new double[] {1, -1, 6};
        double[] p3 = new double[] {-1, -1, 6};
        double[] p4 = new double[] {-1, 1, 6};
        double[] p5 = new double[] {1, 1, 4};
        double[] p6 = new double[] {1, -1, 4};
        double[] p7 = new double[] {-1, -1, 4};
        double[] p8 = new double[] {-1, 1, 4};

        shape[0] = new Line(p1, p2);
        shape[1] = new Line(p2, p3);
        shape[2] = new Line(p3, p4);
        shape[3] = new Line(p4, p1);

        shape[4] = new Line(p1, p5);
        shape[5] = new Line(p2, p6);
        shape[6] = new Line(p3, p7);
        shape[7] = new Line(p4, p8);

        shape[8] = new Line(p5, p6);
        shape[9] = new Line(p6, p7);
        shape[10] = new Line(p7, p8);
        shape[11] = new Line(p8, p5);

        return shape;
    }
    public Line[] newTetrahedron() {
        Line[] shape = new Line[6];
        
        double[] p1 = new double[] {1.5, 0, 5 + 1.5 / Math.sqrt(2)};
        double[] p2 = new double[] {-1.5, 0, 5 + 1.5 / Math.sqrt(2)};
        double[] p3 = new double[] {0, 1.5, 5 - 1.5 / Math.sqrt(2)};
        double[] p4 = new double[] {0, -1.5, 5 - 1.5 / Math.sqrt(2)};
        
        shape[0] = new Line(p1, p2);
        shape[1] = new Line(p1, p3);
        shape[2] = new Line(p1, p4);
        shape[3] = new Line(p2, p3);
        shape[4] = new Line(p2, p4);
        shape[5] = new Line(p3, p4);

        return shape;
    }
    public Line[] newOctahedron() {
        Line[] shape = new Line[12];
        
        double[] p1 = new double[] {0, 0, 6.75};
        double[] p2 = new double[] {1.75, 0, 5};
        double[] p3 = new double[] {-1.75, 0, 5};
        double[] p4 = new double[] {0, 1.75, 5};
        double[] p5 = new double[] {0, -1.75, 5};
        double[] p6 = new double[] {0, 0, 3.25};
        
        shape[0] = new Line(p1, p2);
        shape[1] = new Line(p1, p3);
        shape[2] = new Line(p1, p4);
        shape[3] = new Line(p1, p5);

        shape[4] = new Line(p2, p4);
        shape[5] = new Line(p2, p5);
        shape[6] = new Line(p3, p4);
        shape[7] = new Line(p3, p5);

        shape[8] = new Line(p6, p2);
        shape[9] = new Line(p6, p3);
        shape[10] = new Line(p6, p4);
        shape[11] = new Line(p6, p5);

        return shape;
    }
    public Line[] newDodecahedron() {
        Line[] shape = new Line[30];

        double[] p1 = new double[] {invPhi, 0, phi + 5};
        double[] p2 = new double[] {-invPhi, 0, phi + 5};

        double[] p3 = new double[] {1, 1, 1 + 5};
        double[] p4 = new double[] {1, -1, 1 + 5};
        double[] p5 = new double[] {-1, -1, 1 + 5};
        double[] p6 = new double[] {-1, 1, 1 + 5};

        double[] p7 = new double[] {0, phi, invPhi + 5};
        double[] p8 = new double[] {0, phi, -invPhi + 5};
        double[] p9 = new double[] {0, -phi, -invPhi + 5};
        double[] p10 = new double[] {0, -phi, invPhi + 5};

        double[] p11 = new double[] {phi, invPhi, 0 + 5};
        double[] p12 = new double[] {phi, -invPhi, 0 + 5};
        double[] p13 = new double[] {-phi, -invPhi, 0 + 5};
        double[] p14 = new double[] {-phi, invPhi, 0 + 5};

        double[] p15 = new double[] {1, 1, -1 + 5};
        double[] p16 = new double[] {1, -1, -1 + 5};
        double[] p17 = new double[] {-1, -1, -1 + 5};
        double[] p18 = new double[] {-1, 1, -1 + 5};

        double[] p19 = new double[] {invPhi, 0, -phi + 5};
        double[] p20 = new double[] {-invPhi, 0, -phi + 5};

        shape[0] = new Line(p1, p2);

        shape[1] = new Line(p1, p3);
        shape[2] = new Line(p1, p4);
        shape[3] = new Line(p2, p5);
        shape[4] = new Line(p2, p6);

        shape[5] = new Line(p7, p8);
        shape[6] = new Line(p9, p10);

        shape[7] = new Line(p3, p7);
        shape[8] = new Line(p6, p7);
        shape[9] = new Line(p4, p10);
        shape[10] = new Line(p5, p10);

        shape[11] = new Line(p11, p12);
        shape[12] = new Line(p13, p14);

        shape[13] = new Line(p3, p11);
        shape[14] = new Line(p4, p12);
        shape[15] = new Line(p5, p13);
        shape[16] = new Line(p6, p14);

        shape[17] = new Line(p8, p15);
        shape[19] = new Line(p9, p16);
        shape[20] = new Line(p9, p17);
        shape[18] = new Line(p8, p18);

        shape[21] = new Line(p11, p15);
        shape[22] = new Line(p12, p16);
        shape[23] = new Line(p13, p17);
        shape[24] = new Line(p14, p18);

        shape[25] = new Line(p19, p20);

        shape[26] = new Line(p15, p19);
        shape[27] = new Line(p16, p19);
        shape[28] = new Line(p17, p20);
        shape[29] = new Line(p18, p20);

        return shape;
    }
    public Line[] newIcosahedron() {
        Line[] shape = new Line[30];

        double[] p1 = new double[] {0, 1, phi + 5};
        double[] p2 = new double[] {0, 1, -phi + 5};
        double[] p3 = new double[] {0, -1, phi + 5};
        double[] p4 = new double[] {0, -1, -phi + 5};

        double[] p5 = new double[] {1, phi, 0 + 5};
        double[] p6 = new double[] {1, -phi, 0 + 5};
        double[] p7 = new double[] {-1, phi, 0 + 5};
        double[] p8 = new double[] {-1, -phi, 0 + 5};

        double[] p9 = new double[] {phi, 0, 1 + 5};
        double[] p10 = new double[] {-phi, 0, 1 + 5};
        double[] p11 = new double[] {phi, 0, -1 + 5};
        double[] p12 = new double[] {-phi, 0, -1 + 5};

        shape[0] = new Line(p1, p3);
        shape[1] = new Line(p2, p4);
        shape[2] = new Line(p5, p7);
        shape[3] = new Line(p6, p8);
        shape[4] = new Line(p9, p11);
        shape[5] = new Line(p10, p12);
        
        shape[6] = new Line(p1, p9);
        shape[7] = new Line(p1, p10);
        shape[8] = new Line(p3, p9);
        shape[9] = new Line(p3, p10);
        
        shape[10] = new Line(p2, p11);
        shape[11] = new Line(p2, p12);
        shape[12] = new Line(p4, p11);
        shape[13] = new Line(p4, p12);

        shape[14] = new Line(p1, p5);
        shape[15] = new Line(p1, p7);
        shape[16] = new Line(p2, p5);
        shape[17] = new Line(p2, p7);
        
        shape[18] = new Line(p3, p6);
        shape[19] = new Line(p3, p8);
        shape[20] = new Line(p4, p6);
        shape[21] = new Line(p4, p8);

        shape[22] = new Line(p5, p9);
        shape[23] = new Line(p5, p11);
        shape[24] = new Line(p6, p9);
        shape[25] = new Line(p6, p11);

        shape[26] = new Line(p7, p10);
        shape[27] = new Line(p7, p12);
        shape[28] = new Line(p8, p10);
        shape[29] = new Line(p8, p12);

        return shape;
    }
}
