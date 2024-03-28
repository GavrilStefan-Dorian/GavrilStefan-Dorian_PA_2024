package org.example;

import com.github.javafaker.Faker;
import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.generate.RandomGnpBipartiteGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ProblemGenerator {
    public Problem generateProblem(int driversCount, int passengersCount, double edgeProbability) {
        Random random = new Random();
        Faker faker = new Faker();

        List<Person> listOfPersons = new ArrayList<>();

        for(int i = 0; i < driversCount; i++) {
            Destination randomDestination = new Destination(faker.address().cityName());
            listOfPersons.add(new Driver(faker.name().firstName(), random.nextInt(18, 60), randomDestination, new ArrayList<>()));
        }

        RandomGnpBipartiteGenerator rngBipartiteGen = new RandomGnpBipartiteGenerator(driversCount, passengersCount, edgeProbability);
        var graph = rngBipartiteGen.createGraph();

        // for each edge, set a destination to the driver and to all it's connected passengers
        for (var edge : graph.edges()) {
            for (var neighbor : graph.neighbors(edge.source())) {
                Destination driverDestination = listOfPersons.get(edge.source()).getDestination();
                listOfPersons.add(new Passenger(faker.name().firstName(), random.nextInt(18, 80), driverDestination));
            }
        }

        return new Problem(listOfPersons, graph);
    }
}
