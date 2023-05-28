package com.example.appnote

public class firebasemodel(t: String, c: String, p1: Boolean, p2: Boolean, p3: Boolean) {

    //Add this

    private var title: String = t;

    private var content: String = c;

    private var pr: Boolean = p1;

    private var pr2: Boolean = p2;

    private var pr3: Boolean = p3;


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