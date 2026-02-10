package Labwork1;

public class Data {
    private int count;
    private double max;
    private double sum;

    public Data(){
        count = 0;
        max = Double.NEGATIVE_INFINITY;
        sum = 0.0;
    }

    public void add(double value){
        count++;
        sum += value;

        if(value > max) max = value;
    }

    public double getAverage(){
        if(count == 0) return 0.0;
        return sum/count;
    }

    public double getMax(){
        if(count == 0) return max;
        return max;
    }
}
