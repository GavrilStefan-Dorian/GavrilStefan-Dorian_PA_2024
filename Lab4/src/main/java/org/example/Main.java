package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        var arrayPersons = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> new Person("P" + i, random.nextInt(60), random.nextBoolean(), random.nextBoolean()))
                .toArray(Person[]::new);

        List<Person> listOfPersons = new ArrayList<>(Arrays.asList(arrayPersons));

        Map<Destination, List<Person>> destMap = new HashMap<>();

        Destination destination1 = new Destination("Botosani");
        Destination destination2 = new Destination("Iasi");
        Destination destination3 = new Destination("Brehuiesti");

        destMap.put(destination1, Arrays.asList(listOfPersons.get(0), listOfPersons.get(1), listOfPersons.get(2)));
        destMap.put(destination2, Arrays.asList(listOfPersons.get(3), listOfPersons.get(6)));
        destMap.put(destination3, Arrays.asList(listOfPersons.get(4), listOfPersons.get(5)));

        listOfPersons.get(0).setDestination(destination1);
        listOfPersons.get(1).setDestination(destination1);
        listOfPersons.get(2).setDestination(destination1);
        listOfPersons.get(3).setDestination(destination2);
        listOfPersons.get(6).setDestination(destination2);
        listOfPersons.get(4).setDestination(destination3);
        listOfPersons.get(4).setDestination(destination3);

        System.out.println("\tList of persons:");
        System.out.println(listOfPersons);

        List<Person> listOfPassengers = new LinkedList<>();
        Set<Person> listOfDrivers = new TreeSet<>();

        listOfPersons.stream()
                .filter(Person::isPassenger)
                .forEach(listOfPassengers::add);

        listOfPersons.stream()
                .filter(Person::isDriver)
                .forEach(listOfDrivers::add);


        System.out.println("\n\tList of Passengers:");
        listOfPassengers.stream().sorted(Comparator.comparing(Person::getName)).forEach(System.out::println);

        System.out.println("\n\tList of Drivers:");
        listOfDrivers.stream().sorted(Comparator.comparing(Person::getAge)).forEach(System.out::println);
    }
}

//Hopcroft-Karp