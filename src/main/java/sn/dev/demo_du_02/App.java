package sn.dev.demo_du_02;

import sn.dev.demo_du_02.entitie.Personne;
import sn.dev.demo_du_02.entitie.Specialiste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demo_du_02");
        EntityManager em = emf.createEntityManager();


        try {
            em.getTransaction().begin();

            Personne personne = new Personne();
            personne.setNom("NomTest");
            personne.setPrenom("PrenomTest");
            em.persist(personne);

            Specialiste specialiste = new Specialiste();
            specialiste.setName("ibrahim");
            em.persist(specialiste);


            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
