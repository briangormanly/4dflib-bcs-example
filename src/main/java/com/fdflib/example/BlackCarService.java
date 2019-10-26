package com.fdflib.example;

import com.fdflib.example.model.Car;
import com.fdflib.example.model.CarMake;
import com.fdflib.example.model.Driver;
import com.fdflib.example.service.CarService;
import com.fdflib.example.service.DriverService;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.persistence.database.DatabaseUtil;
import com.fdflib.service.FdfServices;
import com.fdflib.util.FdfSettings;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brian.gormanly on 10/9/15
 */
public class BlackCarService {
    public static void main(String[] args) {
        System.out.println("Hello 4DF World!"); // Display the string.

        // use the  settings within this method to customize the 4DFLib.  Note, everything in this method is optional.
        setOptionalSettings();

        // Create a array that will hold the classes that make up our 4df data model
        List<Class> myModel = new ArrayList<>();

        // Add our 2 classes
        myModel.add(Driver.class);
        myModel.add(Car.class);

        // call the initialization of library!
        FdfServices.initializeFdfDataModel(myModel);

        // insert some demo data
        try {
            insertSomeData();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        // do a few queries and output the results

    }

    /**
     * Everything set in this method is optional, but useful
     */
    private static void setOptionalSettings() {

        // get the 4dflib settings singleton
        FdfSettings fdfSettings = FdfSettings.getInstance();

        // set the database type and name and connection information
        // PostgreSQL settings
        //fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.POSTGRES;
        //fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_POSTGRES;

        // MySQL settings
        fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.MYSQL;
        fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_MYSQL;

        // Database encoding
        fdfSettings.DB_ENCODING = DatabaseUtil.DatabaseEncoding.UTF8;

        // Application Database name
        fdfSettings.DB_NAME = "blackcardemo";

        // Database host
        fdfSettings.DB_HOST = "localhost";

        // Database user information
        fdfSettings.DB_USER = "blackcar";
        fdfSettings.DB_PASSWORD = "blackcarpass";

        // root user settings are only required for initial database creation.  Once the database is created you
        // should remove this information
        fdfSettings.DB_ROOT_USER = "root";
        fdfSettings.DB_ROOT_PASSWORD = "";

        // set the default system information
        fdfSettings.DEFAULT_SYSTEM_NAME = "Black Car Core API";
        fdfSettings.DEFAULT_SYSTEM_DESCRIPTION = "Central API service for the Black Car Application";

        // set the default tenant information
        fdfSettings.DEFAULT_TENANT_NAME = "BlackCar";
        fdfSettings.DEFAULT_TENANT_DESRIPTION = "Main system Tenant";
        fdfSettings.DEFAULT_TENANT_IS_PRIMARY = true;
        fdfSettings.DEFAULT_TENANT_WEBSITE = "http://www.4dflib.com";

    }

    private static void insertSomeData() throws InterruptedException {
        DriverService ds = new DriverService();
        CarService cs = new CarService();

        // create a few of drivers
        Driver sam = new Driver();
        sam.firstName = "Sam";
        sam.lastName = "Holden";
        sam.phoneNumber = "212-555-1212";
        ds.saveDriver(sam);

        Driver harry = new Driver();
        harry.firstName = "Harry";
        harry.lastName = "Smith";
        harry.phoneNumber = "212-555-1313";
        harry = ds.saveDriver(harry);

        Driver jack = new Driver();
        jack.firstName = "Jack";
        jack.lastName = "Johnson";
        jack.phoneNumber = "212-555-1414";
        jack = ds.saveDriver(jack);

        Driver brian = new Driver();
        jack.firstName = "Brian";
        jack.lastName = "G";
        jack.phoneNumber = "845-555-1114";
        brian = ds.saveDriver(brian);

        Car mufasa = new Car();
        mufasa.name = "Mufasa";
        mufasa.color = "Teal";
        mufasa.isInNeedOfRepair = false;
        mufasa.description = "Total awesomeness";
        mufasa.make = CarMake.PONTIAC;
        mufasa.model = "Trans Am";
        mufasa.year = 1983;
        cs.saveCar(mufasa);

        Car ss = new Car();
        ss.name = "sliver ss";
        ss.color = "Sliver";
        ss.isInNeedOfRepair = false;
        ss.description = "Nice Sleeper";
        ss.make = CarMake.CHEVY;
        ss.model = "SS";
        ss.year = 2014;
        cs.saveCar(ss);

        Car pbox = new Car();
        pbox.name = "Brians project";
        pbox.color = "Gray";
        pbox.isInNeedOfRepair = false;
        pbox.description = "Roadster";
        pbox.make = CarMake.PORSCHE;
        pbox.model = "Boxster s";
        pbox.year = 2001;
        pbox.currentDriverId = brian.id;
        cs.saveCar(pbox);

        Car pilot = new Car();
        pilot.name = "Current Family Hauler";
        pilot.color = "Maroon";
        pilot.isInNeedOfRepair = false;
        pilot.description = "Family Fun!";
        pilot.make = CarMake.HONDA;
        pilot.model = "Pilot";
        pilot.year = 2019;
        pilot.currentDriverId = brian.id;
        cs.saveCar(pilot);

        Car van = new Car();
        van.name = "Minivan o death";
        van.color = "Red";
        van.isInNeedOfRepair = false;
        van.description = "POS";
        van.make = CarMake.CHRYSLER;
        van.model = "Town & Country";
        van.year = 2014;
        cs.saveCar(van);

        Car cv1 = new Car();
        cv1.name = "Medallion 1";
        cv1.color = "Yellow";
        cv1.isInNeedOfRepair = false;
        cv1.description = "NYC has the best taxis";
        cv1.make = CarMake.FORD;
        cv1.model = "LTD Crown Victoria";
        cv1.year = 2000;
        // assign jack to the this car
        cv1.currentDriverId = jack.id;
        cs.saveCar(cv1);

        /*
            Lets do some stuff with the data
         */

        // since we did not assign anyone to drive mufasa, lets do that now
        mufasa = cs.getCarsByName("Mufasa");
        // output the current driver id
        System.out.println("Mufasa Driver Id (shoud be empty): " + mufasa.currentDriverId);
        mufasa.currentDriverId = harry.id;
        cs.saveCar(mufasa);

        // now try again
        mufasa = cs.getCarsByName("Mufasa");
        System.out.println("Mufasa Driver Id: " + mufasa.currentDriverId); // should have an id now!


        // lets also find all cars from the year 2014
        List<Car> cars14 = cs.getCarsByYear(2014);
        for(Car car: cars14) {
            System.out.println("2014 car: " + car.name + " repair status: " + car.isInNeedOfRepair);
        }

        // Wait a few seconds
        Thread.sleep(6000);

        // change all 2014 cars status to needing repair
        for(Car car: cars14) {
            car.isInNeedOfRepair = true;
            cs.saveCar(car);
        }

        // Wait a few seconds
        Thread.sleep(6000);

        // get one of the cars and change it back
        List<Car> cars14new = cs.getCarsByYear(2014);
        cars14new.get(0).isInNeedOfRepair = false;
        cs.saveCar(cars14new.get(0));

        // re-run the query and output the results again, this time with history so we can see the change!
        List<FdfEntity<Car>> cars14withHistory = cs.getCarsByYearWithHistory(2014);
        for(FdfEntity<Car> carWithHistory: cars14withHistory) {
            // first output the cars current status
            System.out.println("2014 car [after update]: " + carWithHistory.current.name
                    + " [current] repair status: " + carWithHistory.current.isInNeedOfRepair);

            System.out.println("----- History -----");
            // Now show the historical records for the car
            for(Car carHistory : carWithHistory.history) {
                System.out.println("Start time: " + carHistory.arsd + " End time: " + carHistory.ared + " repair status: " + carHistory.isInNeedOfRepair);
            }
            System.out.println("___________________");

        }



    }
}