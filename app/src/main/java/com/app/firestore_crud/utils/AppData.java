package com.app.firestore_crud.utils;

import com.app.firestore_crud.models.Event;

/**
 * Created by Manoj Singh on 12-02-2020.
 * immanojsingh007@gmail.com
 */
public class AppData {
    private static final AppData ourInstance = new AppData();
    private Event selectedEvent;

    public static AppData getInstance() {
        return ourInstance;
    }

    private AppData() {
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }
}
