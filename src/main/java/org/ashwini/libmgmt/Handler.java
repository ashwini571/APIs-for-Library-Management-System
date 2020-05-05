package org.ashwini.libmgmt;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Handler {

    public static Object handleRequest(Event event, Context context) throws ResourceNotFoundException {
        BookItem book = null;

        switch(event.getHttpMethod())
        {
            case "POST":
                try{
                    App.putBook(event.getBook());

                    return event.getBook();
                }
                catch(Exception e)
                {
                    return null;
                }
            default:
                break;
        }
        return null;
    }
}