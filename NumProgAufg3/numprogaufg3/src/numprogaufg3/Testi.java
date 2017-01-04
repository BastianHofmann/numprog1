

public class Testi {
    public static void main(String args[]){
        double y[] = {0,1};
        LinearInterpolation li = new LinearInterpolation();
        li.init(0,2,1, y);
        li.evaluate(1);
    }
}
