package BusinessLayer.Workers_BusinessLayer;

public class Pair {
    private int key;
    private String value;

    public Pair(int k, String v){
        this.key = k;
        this.value = v;
    }

    public int getKey() {
        return key;
    }

    public String getValue(){
        return value;
    }
}
