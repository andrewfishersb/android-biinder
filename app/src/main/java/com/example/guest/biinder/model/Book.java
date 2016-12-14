package com.example.guest.biinder.model;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/13/16.
 */
@Parcel
public class Book {

    long likes;
    long dislikes;

    public Book(long likes, long dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Book(){}


    public long getLikes() {
        return likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void addLike() {
        likes += 1;
    }

    public void addDislike() {
        dislikes += 1;
    }


}
