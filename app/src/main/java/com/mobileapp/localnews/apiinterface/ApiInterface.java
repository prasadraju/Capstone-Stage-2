package com.mobileapp.localnews.apiinterface;

import com.mobileapp.localnews.data.NewsPojo;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Venkatesh on 2/25/2017.
 */

public interface ApiInterface {

//    @GET("/videos?api_key=<API_KEY>")
    @GET("/articles?source=techcrunch&apiKey=df9dd2060b9f4f9ea26d8e3bde47eeb3")
    public void getTechNews(Callback<NewsPojo> response);

    @GET("/articles?source=associated-press&sortBy=top&apiKey=df9dd2060b9f4f9ea26d8e3bde47eeb3")
    public void getTopNews(Callback<NewsPojo> response);

    @GET("/articles?source=bbc-sport&sortBy=top&apiKey=df9dd2060b9f4f9ea26d8e3bde47eeb3")
    public void getSportsNews(Callback<NewsPojo> response);

    @GET("/articles?source=bloomberg&sortBy=top&apiKey=df9dd2060b9f4f9ea26d8e3bde47eeb3")
    public void getBusinessNews(Callback<NewsPojo> response);


    @GET("/articles?source=entertainment-weekly&sortBy=top&apiKey=df9dd2060b9f4f9ea26d8e3bde47eeb3")
    public void getEnertainmentNews(Callback<NewsPojo> response);

    @GET("/articles?source=google-news&sortBy=top&apiKey=df9dd2060b9f4f9ea26d8e3bde47eeb3")
    public void getLifestyleNews(Callback<NewsPojo> response);






}
