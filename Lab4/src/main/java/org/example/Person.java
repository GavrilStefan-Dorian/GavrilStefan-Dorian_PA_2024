package org.example;

public abstract class Person implements Comparable<Person>{
    private String name;
    private int age;
    private Destination destination;
    public Person(String name, int age, Destination destination) {
        this.name = name;
        this.age = age;
        this.destination = destination;
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
                '}';
    }
}
