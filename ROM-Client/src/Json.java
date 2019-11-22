import java.io.FileReader;
import java.util.HashMap;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Json {
    JSONArray openJson(String path) {
        JSONParser jsonParser = new JSONParser();
        JSONArray obj = null;
        try {
            FileReader fopen = new FileReader(path);
            obj = (JSONArray) jsonParser.parse(fopen);
        } catch (Exception e) {
            System.out.println(e);
        }
        return obj;
    }

    JSONArray toJson(HashMap<String, Integer> data, Order order) {
        JSONArray list = new JSONArray();
        for (String key : data.keySet()) {
            JSONObject temp = new JSONObject();
            temp.put("name", key);
            temp.put("total", data.get(key));
            list.add(temp);
        }
        list.add(order.getOrderID());
        return list;
    }
}
