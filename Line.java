package test;

public class Line {

    public final float a,b;

    // Line constructor
    public Line(float a, float b){
        this.a=a;
        this.b=b;
    }

    // equation of a line y
    public float f(float x){
        return a*x+b;
    }

}
