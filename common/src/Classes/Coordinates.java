package Classes;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private long x; //Значение поля должно быть больше -97
    private Double y; //Значение поля должно быть больше -79, Поле не может быть null

    public Coordinates(long x, Double y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public void setX(long x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }
    @Override
    public String toString(){
        return "x: " + x + ", y: " + y;
    }
}
