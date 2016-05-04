package qcox.tacoma.uw.edu.farmgame.items;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qcox.tacoma.uw.edu.farmgame.R;

/**
 * Created by Cox Family on 4/30/2016.
 */
public class ItemContent {

    /**
     * An array of sample (Farm) items.
     */
    public static final List<FarmItem> ITEMS = new ArrayList<FarmItem>();

    /**
     * A map of sample (Farm) items, by ID.
     */
    public static final Map<String, FarmItem> ITEM_MAP = new HashMap<String, FarmItem>();


    static {
    addItem(createFarmItem());
    }

    private static void addItem(FarmItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static FarmItem createFarmItem() {
        String id = "basic_wheat";
        String name = "Wheat";
        String itemType = "plant";
        String itemTypeDetail = "";
        int quantity = 1;
        int buyCost = 5;
        int sellCost = 3;
        int growTime = 10;
        String description = "Wheat is easy to grow but not worth much";
        String imageName = "wheat";

        int imageResourceIndex = -1;

        //resource used: http://stackoverflow.com/questions/4427608/android-getting-resource-id-from-string
        try {
            Field idField = R.drawable.class.getDeclaredField(imageName);
            imageResourceIndex = idField.getInt(idField);
        } catch (Exception e) {
            //image will be -1 if no image found.
        }

        return new FarmItem(id, name, quantity, buyCost, sellCost, description, imageResourceIndex);
    }

    public static class FarmItem {
        public String id;
        public String name;
        public int quantity;
        public int buyCost;
        public int sellCost;
        public String description;
        public int image;

        public FarmItem(String id, String name, int quantity, int buyCost, int sellCost, String description, int image) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.buyCost = buyCost;
            this.sellCost = sellCost;
            this.description = description;
            this.image = image;
        }

        @Override
        public String toString() {
            return name + " " + description + " ";
        }
    }

    /**
     * A Farm item representing an item in the game.
     */
//    public static class FarmItem {
//        public final String id;
//        public final String name;
//        public final int quantity;
//        public final int buyCost;
//        public final int sellCost;
//        public final String description;
//        public final int image;
//
//        public FarmItem(String id, String name, int quantity, int buyCost, int sellCost, String description, int image) {
//            this.id = id;
//            this.name = name;
//            this.quantity = quantity;
//            this.buyCost = buyCost;
//            this.sellCost = sellCost;
//            this.description = description;
//            this.image = image;
//        }
//
//        @Override
//        public String toString() {
//            return name + " " + description + " ";
//        }
//    }
}
