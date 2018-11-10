package com.wallmark;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetroApi {

    String API_KEY = "9262024268b7996af5fee96c23c9bf7b";
    String USER_ID = "161355629%40N02";
    String FORMAT = "json";
    String NOJSONCALLBACK = "1";
    String SEARCH_METHOD = "flickr.photos.search";
    String PHOTOSET_LIST_METHOD = "flickr.photosets.getList";
    String PHOTOSET_PHOTO_METHOD = "flickr.photosets.getPhotos";
    String POPULAR_PHOTO_METHOD = "flickr.favorites.getList";
    String PHOTO_GETSIZE_METHOD = "flickr.photos.getSizes";



    String ALL_PHOTO_URL = "rest/?method="+SEARCH_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"";
    String PHOTOSET_PHOTO_URL = "https://api.flickr.com/services/rest/?method="+PHOTOSET_PHOTO_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"&photoset_id=";
    String PHOTO_GETSIZE_URL = "https://api.flickr.com/services/rest/?method="+PHOTO_GETSIZE_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"&photo_id=";
    String PHOTOSET_LIST_URL = "rest/?method="+PHOTOSET_LIST_METHOD+"&api_key="+API_KEY+"&user_id="+USER_ID+"&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"";
    String POPULAR_PHOTO_LIST_URL = "rest/?method="+POPULAR_PHOTO_METHOD+"&api_key="+API_KEY+"&user_id=66956608@N06&format="+FORMAT+"&nojsoncallback="+NOJSONCALLBACK+"";

    @GET(ALL_PHOTO_URL)
    Call<Model> getSerch();

    @GET(POPULAR_PHOTO_LIST_URL)
    Call<Model> getPopular();

    @GET(PHOTOSET_LIST_URL)
    Call<Category> getCategoryList();

    @GET(PHOTOSET_PHOTO_URL)
    Call<CategoryPhoto> getCategotyPhotos(@Query("photoset_id") String id);

    @GET(PHOTO_GETSIZE_URL)
    Call<PhotoSize> getPhotoSize(@Query("photo_id") String id);

    @GET
    Call<String> downloadPhoto(@Url String url);

}
