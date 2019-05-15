package com.example.gerard.socialapp.model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class TransformadorPostAJson {
    static Gson gson = new Gson();

    public static String objetoAJson(Post someObjects) {
        return gson.toJson(someObjects);
    }

    public static Post stringToSomeObject(String data) {
        if (data == null) {
            return new Post();
        }
        Type listType = new TypeToken<Post>() {}.getType();
        return gson.fromJson(data, listType);
    }
}
