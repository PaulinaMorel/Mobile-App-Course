package com.example.appnote

public class firebasemodel(t: String, c: String) {

    //Add this

    private var title: String = t;

    private var content: String = c;


    fun getTitle(): String {

        return title;

    }


    public fun setTitle(title: String): Unit {

        this.title = title;

    }


    public fun getContent(): String {

        return content;

    }


    public fun setContent(content: String): Unit {

        this.content = content;

    }

}