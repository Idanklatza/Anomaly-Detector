package test;

public class CorrelatedFeatures {

    public final String feature1,feature2;
    public final float corrlation;
    public final Line lin_reg;
    public final float threshold;

    public CorrelatedFeatures(String feature1, String feature2, float correlation, Line lin_reg, float threshold) {
        this.feature1 = feature1;
        this.feature2 = feature2;
        this.corrlation = correlation;
        this.lin_reg = lin_reg;
        this.threshold = threshold;
    }

    public String getFeature1(){
        return feature1;
    }

    public String getFeature2(){
        return feature2;
    }

}
