package Labwork1;

public class Temperature {
    private double temperature;
    private char scale;

    public Temperature (){
        this.temperature = 0;
        this.scale = 'C';
    }

    public Temperature (char letter) {
        this.temperature = 0;
        this.scale = letter;
    }

    public Temperature (double temp) {
        this.scale = 'C';
        this.temperature = temp;
    }

    public Temperature (double temp, char letter) {
        this.temperature = temp;
        this.scale = letter;
    }

    public double getCelcius() {
        if(this.scale == 'C') return this.temperature;
        else return 5*(this.temperature - 32)/9;
    }

    public double getFahrenheit() {
        if (this.scale == 'F') return this.temperature;
        else return 9*(this.temperature/5) + 32;
    }

    public void setScale (char letter) {
        this.scale = letter;
    }

    public void setTemp (double temp) {
        this.temperature = temp;
    }

    public void setBoth (double temp, char letter) {
        this.scale = letter;
        this.temperature = temp;
    }

    public char getScale() {
        return scale;
    }
}
