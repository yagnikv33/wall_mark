package com.wallmark;

class MyUtil {

    private static String API_KEY = "9262024268b7996af5fee96c23c9bf7b";
    private static String USER_ID = "161355629%40N02";
    private static String FORMAT = "json";
    private static String NOJSONCALLBACK = "1";
    private static String SEARCH_METHOD = "flickr.photos.search";
    private static String PHOTOSET_LIST_METHOD = "flickr.photosets.getList";
    private static String PHOTOSET_PHOTO_METHOD = "flickr.photosets.getPhotos";
    private static String POPULAR_PHOTO_METHOD = "flickr.favorites.getList";
    private static String PHOTO_GETSIZE_METHOD = "flickr.photos.getSizes";



    static String ALL_PHOTO_URL = "https://api.flickr.com/services/rest/?method="+SEARCH_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"";
    static String PHOTOSET_PHOTO_URL = "https://api.flickr.com/services/rest/?method="+PHOTOSET_PHOTO_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"&photoset_id=";
    static String PHOTO_GETSIZE_URL = "https://api.flickr.com/services/rest/?method="+PHOTO_GETSIZE_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"&photo_id=";
    static String PHOTOSET_LIST_URL = "https://api.flickr.com/services/rest/?method="+PHOTOSET_LIST_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"";
    static String POPULAR_PHOTO_LIST_URL = "https://api.flickr.com/services/rest/?method="+POPULAR_PHOTO_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"";


}
