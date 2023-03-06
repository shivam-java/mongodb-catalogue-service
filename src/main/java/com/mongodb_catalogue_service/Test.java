package com.mongodb_catalogue_service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class Test {

    public static void main(String[] args) throws IOException {
        File file=new File("src/main/resources/default_data/playerBatting.json");
        InputStream inputStream=new FileInputStream(file);
        JsonReader jsonReader=new JsonReader(new InputStreamReader(inputStream));

        Gson gson=new Gson();
        while (jsonReader.hasNext()) {
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            System.out.println(jsonObject.get("profile"));
        }

    }

}
