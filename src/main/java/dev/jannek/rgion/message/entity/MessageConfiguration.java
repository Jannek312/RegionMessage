package dev.jannek.rgion.message.entity;

public class MessageConfiguration {
    private String greeting;
    private String adoption;

    public MessageConfiguration(String greeting, String adoption) {
        this.greeting = greeting;
        this.adoption = adoption;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getAdoption() {
        return adoption;
    }

    public void setAdoption(String adoption) {
        this.adoption = adoption;
    }
}
