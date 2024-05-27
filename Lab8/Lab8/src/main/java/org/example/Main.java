package org.example;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            AuthorDAO authorDAO = new AuthorDAO();

            authorDAO.create("Cristian Vidrascu");
            System.out.println("Autorul a fost adăugat cu succes.");

            int authorIdToFind = 3;
            Author foundAuthor = authorDAO.findById(authorIdToFind);

            if (foundAuthor != null) {
                System.out.println("Autorul găsit cu ID-ul " + authorIdToFind + ": " + foundAuthor.getName());
            } else {
                System.out.println("Autorul cu ID-ul " + authorIdToFind + " nu a fost găsit în baza de date.");
            }

            String authorNameToFind = "Bogdan Patrut";
            Integer foundAuthorIdByName = authorDAO.findByName(authorNameToFind);

            if (foundAuthorIdByName != null) {
                System.out.println("Autorul '" + authorNameToFind + "' a fost găsit cu ID-ul: " + foundAuthorIdByName);
            } else {
                System.out.println("Autorul '" + authorNameToFind + "' nu a fost găsit în baza de date.");
            }

            Database.closeConnection();
        } catch (SQLException e) {
            System.err.println("Eroare la accesarea bazei de date: " + e.getMessage());
            Database.rollback();
        }
    }
}
