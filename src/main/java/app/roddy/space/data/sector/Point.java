package app.roddy.space.data.sector;

public class Point {

    public int x = 0;
    public int y = 0;
    public int z = 0;

    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", x, y, z);
    }

    public double distance(Point p) {
        return distance(p.x, p.y, p.z);
    }

    public double distance(int x, int y, int z) {
        double a = this.x - x;
        double b = this.y - y;
        double c = this.z - z;
        return Math.sqrt((a*a)+(b*b)+(c*c));
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Point)) {
            return false;
        } else if (o == this) {
            return true;
        } else {
            Point p = (Point)o;
            return p.x == x &&
                   p.y == y &&
                   p.z == z;
        }
    }
}
