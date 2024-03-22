package org.example;

public class Person implements Comparable<Person>{
    private String name;
    private int age;
    private Destination destination;

    private boolean isDriver;

    private boolean isPassenger;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        isDriver = false;
        isPassenger = false;
    }

    public Person(String name, int age, boolean isDriver, boolean isPassenger) {
        this.name = name;
        this.age = age;
        this.isDriver = isDriver;
        this.isPassenger = isPassenger;
    }
    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDriver(boolean isDriver) {
        this.isDriver = isDriver;
    }

    public void setPassenger(boolean isPassenger) {
        this.isPassenger = isPassenger;
    }

    public boolean isPassenger(){
        return isPassenger;
    }

    public boolean isDriver(){
        return isDriver;
    }
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public int compareTo(Person o) {
        return o.getName().compareTo(this.getName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", destination=" + destination +
                ", isDriver=" + isDriver +
                ", isPassenger=" + isPassenger +
                '}';
    }
}
