package com.fdflib.example.service;

import com.fdflib.example.model.Driver;
import com.fdflib.model.entity.FdfEntity;
import com.fdflib.service.impl.FdfCommonServices;

/**
 * Created by brian on 3/12/16.
 */
public class DriverService implements FdfCommonServices {
    public Driver saveDriver(Driver driver) {
        if(driver != null) {
            return this.save(Driver.class, driver).current;
        }
        return null;
    }

    public Driver getDriverById(long id) {
        return getDriverWithHistoryById(id).current;

    }

    public FdfEntity<Driver> getDriverWithHistoryById(long id) {
        FdfEntity<Driver> driver = new FdfEntity<>();

        // get the test
        if(id >= 0) {
            driver = this.getEntityById(Driver.class, id);
        }

        return driver;
    }
}
