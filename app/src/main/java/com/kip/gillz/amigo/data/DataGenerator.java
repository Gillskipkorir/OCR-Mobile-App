package com.kip.gillz.amigo.data;

import android.content.Context;
import android.content.res.TypedArray;

import com.kip.gillz.amigo.Allclients.Allclientsbacktask;
import com.kip.gillz.amigo.R;
import com.kip.gillz.amigo.model.People;
import com.kip.gillz.amigo.model.Social;
import com.kip.gillz.amigo.utils.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ResourceType")
public class DataGenerator {

    private static Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }





    /**
     * Generate dummy data people
     *
     * @param ctx android context
     * @return list of object
     */
   /* public static List<People> getPeopleData(Context ctx) {
        List<People> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);
        String meterno_arr[] = ctx.getResources().getStringArray(R.array.Meterno);


        for (int i = 0; i < drw_arr.length(); i++) {
            People obj = new People();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.meterno = meterno_arr[i];
            obj.email = Tools.getEmailFromName(obj.name);
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }

        //todo: order in which people names are shown
        //Collections.reverse(items);
        return items;
    }*/

    public static List<Social> getSocialData(Context ctx) {
        List<Social> items = new ArrayList<>();


        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.social_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.social_names);

       for (int i = 0; i < drw_arr.length(); i++) {
            Social obj = new Social();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }

        Collections.shuffle(items);
        return items;
    }

    private static int getRandomIndex(int max) {
        return r.nextInt(max - 1);
    }
}
