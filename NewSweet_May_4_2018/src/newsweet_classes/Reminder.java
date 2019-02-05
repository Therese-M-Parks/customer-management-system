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
public class Reminder {
    
    StringProperty reminderid = new SimpleStringProperty();
    StringProperty reminderDate = new SimpleStringProperty();
    StringProperty reminderSnoozeIncrement = new SimpleStringProperty();
    StringProperty reminderSnoozeIncrementTypeId = new SimpleStringProperty();
    StringProperty reminderAppointmentId = new SimpleStringProperty();
    StringProperty reminderCreatedBy= new SimpleStringProperty();


    public Reminder(String reminderid, String reminderAppointmentId, String reminderCreatedBy/*, String reminderDate, String reminderSnoozeIncrement,  
            String reminderSnoozeIncrementTypeId*/) {
        this.reminderid.set(reminderid);
//        this.reminderDate.set(reminderDate);
//        this.reminderSnoozeIncrement.set(reminderSnoozeIncrement);
//        this.reminderSnoozeIncrementTypeId.set(reminderSnoozeIncrementTypeId);
        this.reminderAppointmentId.set(reminderAppointmentId);
        this.reminderCreatedBy.set(reminderCreatedBy);
           }

    

    // reminderid
    public String getReminderId() {
        return reminderid.get();
    }

    public void setReminderId(String reminderid) {
        this.reminderid.set(reminderid);
    }

    public StringProperty reminderIdProperty() {
        return reminderid;
    }

       // reminderAppointmentId
    public String getReminderAppointmentId() {
        return reminderAppointmentId.get();
    }

    public void setReminderAppointmentId(String reminderAppointmentId) {
        this.reminderAppointmentId.set(reminderAppointmentId);
    }

    public StringProperty reminderAppointmentIdProperty() {
        return reminderAppointmentId;
    }
    
     // reminderCreatedBy
    public String getReminderCreatedBy() {
        return reminderCreatedBy.get();
    }

    public void setReminderCreatedBy(String reminderCreatedBy) {
        this.reminderCreatedBy.set(reminderCreatedBy);
    }

    public StringProperty reminderCreatedByProperty() {
        return reminderCreatedBy;
    }
    
}
