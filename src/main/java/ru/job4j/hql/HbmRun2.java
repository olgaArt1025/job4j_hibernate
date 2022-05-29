package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun2 {
    public static void main(String[] args) {
        Candidate rsl = null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
/*
           Vacancy one = Vacancy.of("Junior Java");
            Vacancy two = Vacancy.of("Senior Java");
            Vacancy three = Vacancy.of("Middle Java");

            Candidate oneC = Candidate.of("Alex", "Junior Java", 50000);
            Candidate twoC = Candidate.of("Nikolay", "Senior Java", 140000);
            Candidate threeC = Candidate.of("Mihail", "Middle Java", 250000);

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(oneC);
            session.save(twoC);
            session.save(threeC);

            DBVacancy dbVacancy = DBVacancy.of("Проект№1");
            DBVacancy dbVacancy2 = DBVacancy.of("Проект№2");
            session.save(dbVacancy);
            session.save(dbVacancy2);
*/

            rsl = session.createQuery(
                    "select distinct cn from Candidate cn "
                            + "join fetch cn.base a "
                            + "join fetch a.vacancies b "
                            + "where cn.id = :sId", Candidate.class
            ).setParameter("sId", 1).uniqueResult();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        System.out.println(rsl);
    }
}
