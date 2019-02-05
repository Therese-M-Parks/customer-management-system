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
class City {

   // StringProperty cityid = new SimpleStringProperty();
    StringProperty city = new SimpleStringProperty();
    //StringProperty cityCountryId = new SimpleStringProperty();

    public City( String city /*String cityid, String cityCountryId*/) {
      //  this.cityid.set(cityid);
        this.city.set(city);
        //this.city.set(cityCountryId);

    }

//    // cityid 
//    public String getCityid() {
//        return cityid.get();
//    }
//
//    public void setCityId(String cityid) {
//        this.cityid.set(cityid);
//    }
//
//    public StringProperty cityidProperty() {
//        return cityid;
//    }

    // city
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }
    
//    //cityCountryId
//      public String getCityCountryId() {
//        return cityCountryId.get();
//    }
//
//    public void setCityCountryId(String cityCountryId) {
//        this.cityCountryId.set(cityCountryId);
//    }
//
//    public StringProperty cityCountryIdProperty() {
//        return cityCountryId;
//    }
}


