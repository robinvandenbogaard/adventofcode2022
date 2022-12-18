package nl.rockstars.robin.util;

import java.util.Objects;

public final class Point3D {
    private final int x;
    private final int y;
    private final int z;

    Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Point3D of(int x, int y, int z) {
        return new Point3D(x, y, z);
    }

    public Point3D move(Direction d) {
        return new Point3D(x + d.x(), y + d.y(), z);
    }

    public Point3D move(Point3D p) {
        return new Point3D(x + p.x(), y + p.y(), z + p.z());
    }

    public Point3D add(Direction d) {
        return move(d);
    }

    public Point3D up() {
        return move(Point3D.of(0,0,1));
    }

    public Point3D down() {
        return move(Point3D.of(0,0,-1));
    }

    public Point3D north() {
        return move(Point3D.of(0,1,0));
    }

    public Point3D south() {
        return move(Point3D.of(0,-1,0));
    }

    public Point3D east() {
        return move(Point3D.of(1,0,0));
    }
    public Point3D west() {
        return move(Point3D.of(-1,0,0));
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int z() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Point3D) obj;
        return this.x == that.x &&
                this.y == that.y &&
                this.z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Point[" +
                "x=" + x + ", " +
                "y=" + y + ", " +
                "z=" + z + ']';
    }
}
