package com.pixels.wallpapers;

import java.util.ArrayList;

public class Model {

    public static ArrayList<Item> Items;

    public static void LoadModel() {

        Items = new ArrayList<Item>();
        Items.add(new Item(1, "i1.jpg"));
        Items.add(new Item(2, "i2.jpg"));
        Items.add(new Item(3, "i3.jpg"));
        Items.add(new Item(4, "i4.jpg"));
        Items.add(new Item(5, "i5.jpg"));
        Items.add(new Item(6, "i6.jpg"));
        Items.add(new Item(7, "i7.jpg"));
        Items.add(new Item(8, "i8.jpg"));
        Items.add(new Item(9, "i9.jpg"));
        Items.add(new Item(10, "i10.jpg"));
        Items.add(new Item(11, "i11.jpg"));
        Items.add(new Item(12, "i12.jpg"));
        Items.add(new Item(13, "i13.jpg"));
        Items.add(new Item(14, "i14.jpg"));
        Items.add(new Item(15, "i15.jpg"));
        Items.add(new Item(16, "i16.jpg"));
        Items.add(new Item(17, "i17.jpg"));
        Items.add(new Item(18, "i18.jpg"));
        Items.add(new Item(19, "i19.jpg"));
        Items.add(new Item(20, "i20.jpg"));
        Items.add(new Item(21, "i21.jpg"));
        Items.add(new Item(22, "i22.jpg"));
        Items.add(new Item(23, "i23.jpg"));
        Items.add(new Item(24, "i24.jpg"));
        Items.add(new Item(25, "i25.jpg"));
        Items.add(new Item(26, "i26.jpg"));
        
        
        

    }

    public static Item GetbyId(int id){

        for(Item item : Items) {
            if (item.Id == id) {
                return item;
            }
        }
        return null;
    }

}
