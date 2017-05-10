package no.hioa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Usman on 08.05.2017.
 */

@Controller
public class FrontController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }




    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public String data(@ModelAttribute("num1") int num1,
                       @ModelAttribute("num2") int num2,
                       Model model){


        String addService = "http://sample-env-9.mufzz4vpsq.eu-central-1.elasticbeanstalk.com/"+num1+"/"+num2; //just a string
        String subService = "http://sample-env-10.mufzz4vpsq.eu-central-1.elasticbeanstalk.com/"+num1+"/"+num2;

        
        try{
            URL url = new URL(addService);
            HttpURLConnection requestAdd = (HttpURLConnection) url.openConnection();
            requestAdd.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser();
            JsonElement addServiceResult = jp.parse(new InputStreamReader((InputStream) requestAdd.getContent()));


            url = new URL(subService);
            HttpURLConnection requestSub = (HttpURLConnection) url.openConnection();
            requestSub.connect();

            // Convert to a JSON object to print data
            jp = new JsonParser(); //from gson
            JsonElement subServiceResult = jp.parse(new InputStreamReader((InputStream) requestSub.getContent()));

            model.addAttribute("addService", addServiceResult);
            model.addAttribute("subService", subServiceResult);
            model.addAttribute("result", true);

        }catch (Exception e){}

        return "index";
    }

}
