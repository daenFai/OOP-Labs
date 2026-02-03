public class StarTriangle {
    private int count;

    public StarTriangle(int cnt){
        this.count = cnt;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= count; i++) {
            for(int j = 0; j < i; j++) {
                sb.append("[*]");
            }
            if(i < count) sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args){
        StarTriangle small = new StarTriangle(3);
        System.out.println(small.toString());
    }
}
