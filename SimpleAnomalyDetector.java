package test;

import java.util.ArrayList;
import java.util.List;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector{

    ArrayList<CorrelatedFeatures> cf;
    private float correlationThreshold;

    public SimpleAnomalyDetector() {cf=new ArrayList<>();}

    @Override
    public void learnNormal(TimeSeries ts) {
        ArrayList<String> attrs=ts.getAttributes();
        int len=ts.getRowSize();
        float values[][]=new float[attrs.size()][len];
        for(int i=0;i<attrs.size();i++){
            for(int j=0;j<ts.getRowSize();j++){
                values[i][j]=ts.getAttributeData(attrs.get(i)).get(j);
            }
        }
        for(int i=0;i<attrs.size();i++){
            for(int j=i+1;j<attrs.size();j++){
                float p=StatLib.pearson(values[i],values[j]);
                if(Math.abs(p)>0.9){
                    Point ps[]=toPoints(ts.getAttributeData(attrs.get(i)),ts.getAttributeData(attrs.get(j)));
                    Line lin_reg=StatLib.linear_reg(ps);
                    float threshold=findThreshold(ps,lin_reg)*1.1f; // 10% increase
                    CorrelatedFeatures c=new CorrelatedFeatures(attrs.get(i), attrs.get(j), p, lin_reg, threshold);
                    cf.add(c);
                }
            }
        }
    }

    private Point[] toPoints(ArrayList<Float> x, ArrayList<Float> y) {
        Point[] ps=new Point[x.size()];
        for(int i=0;i<ps.length;i++)
            ps[i]=new Point(x.get(i),y.get(i));
        return ps;
    }

    private float findThreshold(Point ps[],Line rl){
        float max=0;
        for(int i=0;i<ps.length;i++){
            float d=Math.abs(ps[i].y - rl.f(ps[i].x));
            if(d>max)
                max=d;
        }
        return max;
    }


    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        ArrayList<AnomalyReport> v=new ArrayList<>();
        for(CorrelatedFeatures c : cf) {
            ArrayList<Float> x=ts.getAttributeData(c.feature1);
            ArrayList<Float> y=ts.getAttributeData(c.feature2);
            for(int i=0;i<x.size();i++){
                if(Math.abs(y.get(i) - c.lin_reg.f(x.get(i)))>c.threshold){
                    String d=c.feature1 + "-" + c.feature2;
                    v.add(new AnomalyReport(d,(i+1)));
                }
            }
        }
        return v;
    }

    public List<CorrelatedFeatures> getNormalModel(){return cf;}

    public void setCorrelationThreshold(float threshold) {correlationThreshold=threshold;}
}
