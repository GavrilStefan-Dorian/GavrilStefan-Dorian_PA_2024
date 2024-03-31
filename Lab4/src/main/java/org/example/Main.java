package org.example;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Faker faker = new Faker();

        var listDestinations = IntStream.rangeClosed(0, 10)
                .mapToObj(i -> new Destination(faker.address().cityName()))
                .toList();

        int listDestinationsLength = listDestinations.size();

        var arrayPersons = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> {
                    Destination randomDestination = listDestinations.get(random.nextInt(listDestinationsLength - 1));

                    //boolean used for deciding randomly which is Passenger or Driver
                    if(random.nextBoolean()) {
                        return new Passenger(faker.name().firstName(), random.nextInt(18,80), randomDestination);
                    }
                    else{
                        //start from base destinations list, remove at random indexes to create random list

                        List<Destination> listRandomDestinations = new ArrayList<>(listDestinations);

                        int randomDestinationsCount = random.nextInt(listDestinationsLength - 1);
                        int randomDestinationIndex = random.nextInt(listRandomDestinations.size() - 1);

                        for(int j = 0; j < randomDestinationsCount; j++, randomDestinationIndex = random.nextInt(listRandomDestinations.size() - 1))
                            listRandomDestinations.remove(randomDestinationIndex);

                        return new Driver(faker.name().firstName(), random.nextInt(18,60), randomDestination, listRandomDestinations);
                    }
                })
                .toArray(Person[]::new);

        List<Person> listOfPersons = new ArrayList<>(Arrays.asList(arrayPersons));

        //Map<Destination, List<Person>> destMap = new HashMap<>();

//        Destination destination1 = new Destination("Botosani");
//        Destination destination2 = new Destination("Iasi");
//        Destination destination3 = new Destination("Brehuiesti");
//
//        destMap.put(destination1, Arrays.asList(listOfPersons.get(0), listOfPersons.get(1), listOfPersons.get(2)));
//        destMap.put(destination2, Arrays.asList(listOfPersons.get(3), listOfPersons.get(6)));
//        destMap.put(destination3, Arrays.asList(listOfPersons.get(4), listOfPersons.get(5)));

//        listOfPersons.get(0).setDestination(destination1);
//        listOfPersons.get(1).setDestination(destination1);
//        listOfPersons.get(2).setDestination(destination1);
//        listOfPersons.get(3).setDestination(destination2);
//        listOfPersons.get(6).setDestination(destination2);
//        listOfPersons.get(4).setDestination(destination3);
//        listOfPersons.get(4).setDestination(destination3);

        System.out.println("\tList of persons:");
        System.out.println(listOfPersons);

        List<Person> listOfPassengers = new LinkedList<>();
        Set<Person> listOfDrivers = new TreeSet<>();

        listOfPersons.stream()
                .filter(p -> p instanceof Passenger)
                .forEach(listOfPassengers::add);

        listOfPersons.stream()
                .filter(p -> p instanceof Driver)
                .forEach(listOfDrivers::add);


        System.out.println("\n\tList of Passengers:");
        listOfPassengers.stream().sorted(Comparator.comparing(Person::getName)).forEach(System.out::println);

        System.out.println("\n\tList of Drivers:");
        listOfDrivers.stream().sorted(Comparator.comparing(Person::getAge)).forEach(System.out::println);

        System.out.println("\n\n\nHomework\n\n");

        Problem problem = new Problem(listOfPersons);

        System.out.println("Passed list");
        System.out.println(problem.GetDestinationsList());
        problem.createMapDestinationToPeople();
        System.out.println("Map");
        System.out.println(problem.getDestinationMap());

        Solution solution = new Solution(problem);
        System.out.println("Greedy matching");
        System.out.println(solution.greedyMatch());

        System.out.println("\n\n\nBonus\n\n");

        ProblemGenerator problemGenerator = new ProblemGenerator();
        Problem problem2 = problemGenerator.generateProblem(500, 500, 0.1);
        Solution solution2 = new Solution(problem2);

//        System.out.println(problem2.getListOfPassengers());
//        System.out.println(problem2.getListOfDrivers());
        System.out.println("\nGreedy\n");
        System.out.println(solution2.greedyMatch());
        System.out.println("\nHopcroft\n");
        System.out.println(solution2.hopcroftKarpMatch());
        System.out.println("\nGreedy\n");
        System.out.println(solution2.greedyMatch());
        System.out.println("\nHopcroft\n");
        System.out.println(solution2.hopcroftKarpMatch());

    }
}
