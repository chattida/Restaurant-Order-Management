import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class Main {
    private static JSONArray json;
    private static ArrayList now;

    public static void main(String[] args) {
        Network.createSocket();
        new GUI();
        now = new ArrayList();
        while (true) {
            json = Network.readSocket();
            now.add(json);
            GUI.reframe(0);
        }
    }

    public static ArrayList getNow() {
        return now;
    }

    public static void removeNow(int fin) {
        for (int i = 0; i < now.size(); i++) {
            JSONArray check = (JSONArray) now.get(i);
            if ((int) check.get(check.size() - 1) == fin) {
                now.remove(i);
            }
        }
    }

    public static Object[][] genTable(int position) {
        JSONArray data = (JSONArray) now.get(position);
        Object[][] mysave = new Object[data.size()-1][2];
        for(int i=0; i<data.size()-1; i++) {
            JSONObject item = (JSONObject) data.get(i);
            mysave[i][0] = "  " + item.get("name");
            mysave[i][1] = item.get("total");
        }
        return mysave;
    }

    public static int getIDOrder(int position) {
        JSONArray data = (JSONArray) now.get(position);
        return (int) data.get(data.size() - 1);
    }
}
