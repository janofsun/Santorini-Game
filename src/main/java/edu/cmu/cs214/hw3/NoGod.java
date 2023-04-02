package edu.cmu.cs214.hw3;

public class NoGod implements God {
    @Override
    public String getName() {
        return "No God";
    }
    @Override
    public Boolean getMethod(String name) {
        return false;
    }
}
