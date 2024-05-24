package com.example.lab9;

import com.example.lab9.factories.AbstractFactory;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;

import java.time.Period;
public class ChocoSolver {
    AbstractFactory factory;

    public ChocoSolver(AbstractFactory factory) {
        this.factory = factory;
    }

    public void findBookSet(int k, int p) {

        var bookRepository = factory.createBookRepository();

        var bookList = bookRepository.findAll();

        Model model = new Model("CSP Model");

        int numBooks = bookList.size();
        BoolVar[] selectedBooks = model.boolVarArray("selected", numBooks);

        model.sum(selectedBooks, ">=", k).post();

        // x[i] + x[j] <= 1
        for (int i = 0; i < numBooks; i++) {
            for (int j = i + 1; j < numBooks; j++) {
                if (!canBePartOfSolution(bookList.get(i), bookList.get(j), p)) {
                    model.arithm(selectedBooks[i], "+", selectedBooks[j], "<=", 1).post();
                }
            }
        }

        Solver solver = model.getSolver();
        if (solver.solve()) {
            System.out.println("A set of books is: ");
            for (int i = 0; i < numBooks; i++) {
                if (selectedBooks[i].getValue() == 1) {
                    System.out.println("Selected Book: " + bookList.get(i).getTitle());
                }
            }
        }
    }

    private boolean canBePartOfSolution(com.example.lab9.entities.Book book1, com.example.lab9.entities.Book book2, int p) {
        boolean sameStartingLetter = book1.getTitle().charAt(0) == book2.getTitle().charAt(0);

        // Check if 'p' years apart
        boolean withinPublicationPeriod = Math.abs(
                Period.between(
                        book1.getPublicationDate().toLocalDate(),
                        book2.getPublicationDate().toLocalDate()
                )
                        .getYears())
                <= p;

        return sameStartingLetter && withinPublicationPeriod;
    }
}