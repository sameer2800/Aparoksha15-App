package com.aparoksha.main;

public class Card {

	private String	eventName;
	private int		eventImage;

	public Card(String eventName, int eventImage) {
		this.eventName = eventName;
		this.eventImage = eventImage;
	}

	public String getEventName() {
		return eventName;
	}

	public int getEventImage() {
		return eventImage;
	}
}
