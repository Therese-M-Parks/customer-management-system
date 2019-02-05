/*
 * A class for customer records including name address and phone number
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsweet_classes;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class Customer {

    StringProperty cusID = new SimpleStringProperty();
    StringProperty name = new SimpleStringProperty();
    StringProperty addressId = new SimpleStringProperty();
    StringProperty street = new SimpleStringProperty();
    StringProperty address2 = new SimpleStringProperty();
    StringProperty city = new SimpleStringProperty();
    StringProperty cityId = new SimpleStringProperty();
    StringProperty phone = new SimpleStringProperty();
    StringProperty countryCustomer = new SimpleStringProperty();
    StringProperty countryIdCustomer = new SimpleStringProperty();
    StringProperty zip = new SimpleStringProperty();

    public Customer(String cusID, String name, String addressId, String street, String address2, 
            String phone, String city, String cityId, String zip, String countryCustomer, String countryIdCustomer) {
        this.cusID.set(cusID);
        this.name.set(name);
        this.addressId.set(addressId);
        this.street.set(street);
        this.address2.set(address2);
        this.phone.set(phone);
        this.city.set(city);
        this.cityId.set(cityId);
        this.zip.set(zip);
        this.countryCustomer.set(countryCustomer);
        this.countryIdCustomer.set(countryIdCustomer);

    }



    // cusID??
    public String getCusID() {
        return cusID.get();
    }

    public void setCusID(String cusID) {
        this.cusID.set(cusID);
    }

    public StringProperty cusIDProperty() {
        return cusID;
    }
    

    // name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // addressId
     public String getAddressId() {
        return cusID.get();
    }

    public void setAddressId(String addressId) {
        this.addressId.set(addressId);
    }

    public StringProperty addressIdProperty() {
        return addressId;
    }
//    
    // phone
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    // street
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    // address2
    public String getAddress2() {
        return address2.get();
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    public StringProperty address2Property() {
        return address2;
    }

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

    
    // cityId
    public String getCityId() {
        return cityId.get();
    }

    public void setCityId(String cityId) {
        this.cityId.set(cityId);
    }

    public StringProperty cityIdProperty() {
        return cityId;
    } 
    
    // country 
    public String getCountryCustomer() {
        return countryCustomer.get();
    }

    public void setCountryCustomer(String countryCustomer) {
        this.countryCustomer.set(countryCustomer);
    }

    public StringProperty countryCustomerProperty() {
        return countryCustomer;
    }
    
     // countryId
    public String getCountryIDCustomer() {
        return countryIdCustomer.get();
    }

    public void setCountryIDCustomer(String countryIdCustomer) {
        this.countryIdCustomer.set(countryIdCustomer);
    }

    public StringProperty countryIDCustomerProperty() {
        return countryIdCustomer;
    }

    // zip
    public String getZip() {
        return zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public StringProperty zipProperty() {
        return zip;
    }
}
