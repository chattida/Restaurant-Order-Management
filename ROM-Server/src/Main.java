import org.json.simple.JSONArray;

public class Main {
    private static JSONArray json;

    public static void main(String[] args) {
        Network.createSocket();
        while (true) {
            json = Network.readSocket();
            System.out.println(json);
        }
    }
}
