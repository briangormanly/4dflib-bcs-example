package com.fdflib.example.service;

import com.fdflib.example.model.Car;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.model.util.WhereClause;
import com.fdflib.persistence.FdfPersistence;
import com.fdflib.service.impl.FdfCommonServices;
import com.fdflib.model.util.SqlStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brian on 3/12/16.
 */
public class CarService extends FdfCommonServices {

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

    /*
        Library function available for any object that extends CommonState
     */
    public Car getCarById(long id) {
        return getCarWithHistoryById(id).current;

    }

    /*
        Another library function available for any object extending CommonState this one returns an FdfEntity which
        includes current FdfEntity.currrent (returns Car) and historical data FdfEntity.history (returns List<Car>)
     */
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


    /*
        Queries for all cars with the name passed
        Returns each with history as a FdfEntity<Car>
     */
    public FdfEntity<Car> getCarsByNameWithHistory(String name) {
        List<FdfEntity<Car>> carsWithHistory = getEntitiesByValueForPassedField(Car.class, "name", name);
        return carsWithHistory.get(0);
    }

    /*
        Returns the same as the last method, but removes the historical records
        Returns simple Car
     */
    public Car getCarsByName(String name) {
        FdfEntity<Car> carsWithHistory = getCarsByNameWithHistory(name);
        if(carsWithHistory.current != null) {
            return carsWithHistory.current;
        }
        return null;
    }

    /*
        Queries for all cas by model year
        Returns each with history as a List<FdfEntity<Car>>
     */
    public List<FdfEntity<Car>> getCarsByYearWithHistory(int year) {

        List<FdfEntity<Car>> carsByYear
                = getEntitiesByValueForPassedField(Car.class, "year", Integer.toString(year));

        return carsByYear;
    }

    public List<Car> getCarsByYear(int year) {

        List<FdfEntity<Car>> carsByYearWithHistory = getCarsByYearWithHistory(year);
        List<Car> cars = null;
        for(FdfEntity<Car> carWithHistory: carsByYearWithHistory) {
            if(carWithHistory.current != null) {
                cars.add(carWithHistory.current);
            }
        }
        return cars;
    }

    /**
     * "Control Syntax" Want more control over the query? your are in luck! see com.fdflib.model.util.SqlStatement
     * https://github.com/briangormanly/4dflib/blob/master/src/main/java/com/fdflib/model/util/SqlStatement.java
     * contribution credit: Corley Herman
     *
     * @param name
     * @return FdfEntity<Car>
     */
    public FdfEntity<Car> customCarQuery(String name) {
        FdfEntity<Car> car = new FdfEntity<>();

        if(car != null) {

            // do the selection
            WhereClause whereName = new WhereClause();
            whereName.name = "name";
            whereName.operator = WhereClause.Operators.EQUAL;
            whereName.value = name;
            whereName.valueDataType = String.class;

            // do the projection
            List<String> selectAttributes = new ArrayList<>();
            selectAttributes.add("make");
            selectAttributes.add("model");
            selectAttributes.add("year");

            List<Car> returnedStates = SqlStatement.build().select(selectAttributes).where(whereName).run(Car.class);

            // create a List of entities
            return manageReturnedEntity(returnedStates);
        }

        return car;
    }
}
