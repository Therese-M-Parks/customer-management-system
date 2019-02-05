/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsweet_classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author there
 */
public class Appointment {

    StringProperty appointmentId = new SimpleStringProperty();
    StringProperty title = new SimpleStringProperty();
    StringProperty contact = new SimpleStringProperty();
    StringProperty description = new SimpleStringProperty();
    StringProperty start = new SimpleStringProperty();
    StringProperty location = new SimpleStringProperty();
    StringProperty url = new SimpleStringProperty();
    StringProperty end = new SimpleStringProperty();

    public Appointment(String id, String title, String description,  String location, String contact, String url, String start, String end) {
        this.appointmentId.set(id);
        this.title.set(title);
        this.contact.set(contact);
        this.description.set(description);
        this.start.set(start);
        this.end.set(end);
        this.location.set(location);
        this.url.set(url);

    }

    

    // id
    public String getAppointmentId() {
        return appointmentId.get();
    }

    public void setAppointmentId(String id) {
        this.appointmentId.set(id);
    }

    public StringProperty appointmentIdProperty() {
        return appointmentId;
    }

    // title
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    // contact
    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty contactProperty() {
        return contact;
    }

    // description
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // start
    public String getStart() {
        return start.get();
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public StringProperty startProperty() {
        return start;
    }
    
     // end
    public String getEnd() {
        return end.get();
    }

    public void setEnd(String end) {
        this.end.set(end);
    }

    public StringProperty endProperty() {
        return end;
    }
// location

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    // url
    public String getUrl() {
        return url.get();
    }

    public void setUrl(String url) {
        this.url.set(url);
    }

    public StringProperty urlProperty() {
        return url;
    }

   

}
