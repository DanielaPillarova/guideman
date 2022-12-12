package sk.upjs.paz1c.guideman.controllers;

import sk.upjs.paz1c.guideman.storage.User;

public enum LoggedUser {
	
    INSTANCE;
	
    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }


    public void setLoggedUser(User loggedUser) {
        System.out.println("Setting user");
//        if (loggedUser == null) {
//        	this.loggedUser = null;
//        }
        this.loggedUser = loggedUser;
    }
}

