package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    private double x;
    private double y;

    public double getX(){return x; }
    public double getY(){return y; }

    public void setX(double xTemp){ x = xTemp; }
    public void setY(double yTemp){ y = yTemp; }

    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point){
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint(){
        this.x = 0;
        this.y = 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new FunctionPoint(this);
    }

    @Override
    public int hashCode() {
        long longX = Double.doubleToLongBits(x);
        long longY = Double.doubleToLongBits(y);
        int x1 = (int)(longX & 0x00000000FFFFFFFFL);
        int x2 = (int)((longX >> 32) & 0x00000000FFFFFFFFL);
        int y1 = (int)(longY & 0x00000000FFFFFFFFL);
        int y2 = (int)((longY >> 32) & 0x00000000FFFFFFFFL);
        return x1 ^ x2 ^ y1 ^ y2;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())){
            return false;
        }
        FunctionPoint functionPoint = (FunctionPoint) obj;
        return (functionPoint.getX() == this.getX()) && (functionPoint.getY() == this.getY());
    }
}