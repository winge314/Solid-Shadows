public class Projection {
    private final double aspectRatio, focalLength, z1, z2;
    private final int halfScreenWidth, halfScreenHeight;
    public Projection(double fov, double zFar, double zNear, int screenWidth, int screenHeight) {
        // wizardry
        this.halfScreenHeight = (int) (screenHeight / 2.0 + 0.5);
        this.halfScreenWidth = (int) (screenWidth / 2.0 + 0.5);
        fov = Math.toRadians(fov);
        this.aspectRatio = (double) screenHeight / (double) screenWidth;
        this.focalLength = (double)(1.0 / Math.tan(fov / 2.0));
        this.z1 = zFar / (zFar - zNear);
        this.z2 = -z1 * zNear;
    }
    public double[] project(double[] coordinates) {
        double[] output = new double[3];
        output[0] = coordinates[0] * focalLength * aspectRatio;
        output[1] = coordinates[1] * focalLength;
        output[2] = coordinates[2] * (z1 + z2);
        double w = coordinates[2];
        if (w != 0) {
            output[0] /= w;
            output[1] /= w;
            output[2] /= w;
        }
        output[0] += 1.0;
        output[1] += 1.0;
        output[2] += 1.0;
        output[0] *= halfScreenWidth;
        output[1] *= halfScreenHeight;
        return output;
    }
}