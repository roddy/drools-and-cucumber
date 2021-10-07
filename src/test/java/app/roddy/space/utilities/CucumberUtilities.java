package app.roddy.space.utilities;

import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CucumberUtilities {

    public static Map<String, String> dataTableToMap(DataTable dt) {
        Map<String, String> map = new HashMap<>();

        List<List<String>> cells = dt.cells();
        if(cells.size() >= 2) {
            List<String> header = cells.get(0);
            List<String> row = cells.get(1);
            for(int j = 0; j < row.size() && j < header.size(); j++) {
                String heading = header.get(j);
                String value = row.get(j);
                map.put(heading, value);
            }
        } else {
            throw new IllegalArgumentException("Invalid data table. Cannot convert to map because there are insufficient rows. At minimum this method requires a datatable with a header row (first) and a data row (second).");
        }
        return map;
    }

    public static List<Map<String, String>> dataTableToMaps(DataTable dt) {
        List<Map<String, String>> maps = new ArrayList<>();

        List<List<String>> cells = dt.cells();
        if(cells.size() >= 2) {
            List<String> header = cells.get(0);
            for (int i = 1; i < cells.size(); i++) {
                Map<String, String> rowMap = new HashMap<>();
                List<String> row = cells.get(i);
                for (int j = 0; j < row.size() && j < header.size(); j++) {
                    String heading = header.get(j);
                    String value = row.get(j);
                    rowMap.put(heading, value);
                }
                maps.add(rowMap);
            }
        } else {
            throw new IllegalArgumentException("Invalid data table. Cannot convert to maps because there are insufficient rows. At minimum this method requires a datatable with a header row (first) and a data row (second).");
        }
        return maps;
    }
}
