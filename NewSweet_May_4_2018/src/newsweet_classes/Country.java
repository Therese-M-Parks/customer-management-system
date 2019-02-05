/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsweet_classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author there
 */
public class Country {

    StringProperty countryId = new SimpleStringProperty();
    StringProperty country = new SimpleStringProperty();

    public Country(String countryId, String country) {
        this.countryId.set(countryId);
        this.country.set(country);

    }

     // country 
    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty countryProperty() {
        return country;
    }
    
     // countryId
    public String getCountryID() {
        return countryId.get();
    }

    public void setCountryID(String countryId) {
        this.countryId.set(countryId);
    }

    public StringProperty countryIDProperty() {
        return countryId;
    }
}
