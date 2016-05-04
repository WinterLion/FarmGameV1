package qcox.tacoma.uw.edu.farmgame.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import qcox.tacoma.uw.edu.farmgame.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 *
 */
public class PlantItems implements Serializable {

    public String name;
    public int buyCost;
    public int sellCost;
    public int growTime;
    public String description;
    public String imageName;
    public int imageResourceIndex;

    public static final String ID="id", NAME = "name", BUYCOST = "buyCost", SELLCOST = "sellCost",
    GROWTIME = "growTime", DESCRIPTION = "description", IMAGENAME = "imageName";

    public PlantItems( String name, int buyCost, int sellCost, int growTime, String description, String imageName) {
        this.name = name;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
        this.growTime = growTime;
        this.description = description;
        this.imageName = imageName;
        this.imageResourceIndex = -1;

        //resource used: http://stackoverflow.com/questions/4427608/android-getting-resource-id-from-string
        try {
            Field idField = R.drawable.class.getDeclaredField(imageName);
            imageResourceIndex = idField.getInt(idField);
        } catch (Exception e) {
            //image will be -1 if no image found.
        }
    }

    @Override
    public String toString() {
        return name + " " + description + " ";
    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns plant item list if success.
     * @param plantsJSON
     * @return reason or null if successful.
     */
    public static String parsePlantsJSONJSON(String plantsJSON, List<PlantItems> plantItemsList) {
        String reason = null;
        if (plantsJSON != null) {
            try {
                JSONArray arr = new JSONArray(plantsJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String name = obj.getString(PlantItems.NAME);
                    String sell = obj.getString(PlantItems.SELLCOST);
                    int sellInt = Integer.parseInt(sell);

                    PlantItems plantItem = new PlantItems(obj.getString(PlantItems.NAME), Integer.parseInt(obj.getString(PlantItems.BUYCOST)),
                            Integer.parseInt(obj.getString(PlantItems.SELLCOST)),Integer.parseInt(obj.getString(PlantItems.GROWTIME)),obj.getString(PlantItems.DESCRIPTION),
                            obj.getString(PlantItems.IMAGENAME));
                    plantItemsList.add(plantItem);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        System.out.print(reason);
        return reason;
    }
}
