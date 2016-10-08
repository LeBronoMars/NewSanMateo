package sanmateo.com.profileapp.singletons;

import java.util.ArrayList;

import sanmateo.com.profileapp.models.response.Gallery;

/**
 * Created by rsbulanon on 10/8/16.
 */

public class GalleriesSingleton {
    private static final GalleriesSingleton GALLERIES_SINGLETON = new GalleriesSingleton();
    private ArrayList<Gallery> galleries = new ArrayList<>();

    private GalleriesSingleton() {

    }

    public static GalleriesSingleton getInstance() {
        return GALLERIES_SINGLETON;
    }

    public ArrayList<Gallery> getGalleries() {
        return galleries;
    }
}
