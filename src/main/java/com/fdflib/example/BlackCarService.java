package com.fdflib.example;

import com.fdflib.example.model.Car;
import com.fdflib.example.model.Driver;
import com.fdflib.model.state.CommonState;
import com.fdflib.persistence.database.DatabaseUtil;
import com.fdflib.service.CommonServices;
import com.fdflib.service.SystemServices;
import com.fdflib.service.TenantServices;
import com.fdflib.util.FdfSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by brian.gormanly on 10/9/15
 */
public class BlackCarService {
    public static void main(String[] args) {
        System.out.println("Hello World!"); // Display the string.

        // Create a array that will hold the classes that make up our 4df data model
        List<Class> myModel = new ArrayList<>();

        // Add our 2 classes
        myModel.add(Driver.class);
        myModel.add(Car.class);

        // call the initialization of library!
        CommonServices.initializeFdfDataModel(myModel);



    }
}
