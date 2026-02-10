public class Time {

    private int hour;
    private int minute;
    private int second;

    public Time(int h, int m, int s){
        this.hour = h;
        this.minute = m;
        this.second = s;
    }

    public String Universal(){
        StringBuilder sb = new StringBuilder();

        if(hour < 10) sb.append("0" + hour + ":");
        else sb.append(hour + ":");

        if(minute < 10) sb.append("0" + minute + ":");
        else sb.append(minute + ":");

        if(second < 10) sb.append("0" + second);
        else sb.append(second);

        return sb.toString();
    }

    public String Standard(){

        boolean next = false;
        StringBuilder sb = new StringBuilder();

        if(hour > 12){
            next = true;
            hour -= 12;
        }

        if(hour < 10) sb.append("0" + hour + ":");
        else sb.append(hour + ":");

        if(minute < 10) sb.append("0" + minute + ":");
        else sb.append(minute + ":");

        if(second < 10) sb.append("0" + second);
        else sb.append(second);

        if(next) sb.append(" PM");
        else sb.append(" AM");

        return sb.toString();
    }

    public Time add(Time t1, Time t2) {
        int newHour = t1.hour + t2.hour;
        int newMinute = t1.minute + t2.minute;
        int newSecond = t1.second + t2.second;

        if (newSecond >= 60){
            newSecond -= 60;
            newMinute++;
        }

        if (newMinute >= 60){
            newMinute -= 60;
            newHour++;
        }

        if (newHour >= 24){
            newHour -= 24;
        }

        return new Time(newHour, newMinute, newSecond);
    }

    public static void main(String[] args){
        Time t = new Time(23, 5, 80);
        System.out.println(t.Universal());
        System.out.println(t.Standard());
        Time t2 = new Time(4, 24, 33);
        t.add(t, t2);
        System.out.println(t.Standard());
    }
}
