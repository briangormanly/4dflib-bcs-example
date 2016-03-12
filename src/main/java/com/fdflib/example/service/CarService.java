package com.fdflib.example.service;

import com.fdflib.example.model.Car;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.WhereClause;
import com.fdflib.persistence.FdfPersistence;
import com.fdflib.service.impl.FdfCommonServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brian on 3/12/16.
 */
public class CarService implements FdfCommonServices {

    public Car saveCar(Car car) {
        if(car != null) {
            System.out.println("Incomming id : " + car.id);
            // name is unique for car so we are going to check for existing one first
            Car existingCar = getCarsByName(car.name);
            if(existingCar != null) {
                // if a match is found, just set the id to match the existing one and it will automatically update
                // instead of insert.
                car.id = existingCar.id;
                System.out.println("Found id: " + car.id);
            }

            if (car != null) {
                return this.save(Car.class, car).current;
            }
        }
        return null;
    }

    public Car getCarById(long id) {
        return getCarWithHistoryById(id).current;

    }

    public FdfEntity<Car> getCarWithHistoryById(long id) {
        FdfEntity<Car> car = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            car = this.getEntityById(Car.class, id);

            // get the drivers
            car.current.currentDriver = new DriverService().getDriverById(car.current.currentDriverId);
            for(Car carHistory: car.history) {
                carHistory.currentDriver = new DriverService().getDriverById(carHistory.currentDriverId);
            }
        }

        return car;
    }

    public Car getCarsByName(String name) {
        FdfEntity<Car> carWithHistory = getCarByNameWithHistory(name);
        if(carWithHistory != null && carWithHistory.current != null) {
            return carWithHistory.current;
        }
        return null;
    }

    public FdfEntity<Car> getCarByNameWithHistory(String name) {
        FdfEntity<Car> car = new FdfEntity<>();

        if(car != null) {
            // create the where statement for the query
            List<WhereClause> whereStatement = new ArrayList<>();

            // check that deleted records are not returned
            WhereClause whereDf = new WhereClause();
            whereDf.name = "df";
            whereDf.operator = WhereClause.Operators.IS_NOT;
            whereDf.value = "true";
            whereDf.valueDataType = Boolean.class;

            // add the id check
            WhereClause whereId = new WhereClause();
            whereId.conditional = WhereClause.CONDITIONALS.AND;
            whereId.name = "name";
            whereId.operator = WhereClause.Operators.EQUAL;
            whereId.value = name;
            whereId.valueDataType = String.class;

            whereStatement.add(whereDf);
            whereStatement.add(whereId);

            // do the query
            List<Car> returnedStates =
                    FdfPersistence.getInstance().selectQuery(Car.class, null, whereStatement);

            // create a List of entities
            return manageReturnedEntity(returnedStates);
        }

        return car;
    }
}
