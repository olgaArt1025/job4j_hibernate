package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Alex", "Junior Java", 50000);
            Candidate two = Candidate.of("Nikolay", "Senior Java", 140000);
            Candidate three = Candidate.of("Mihail", "Middle Java", 250000);

            session.save(one);
            session.save(two);
            session.save(three);

            /*
            Query query = session.createQuery("from Candidate");
            for (Object cn : query.list()) {
                System.out.println(cn);
            }

            Query query = session.createQuery("from Candidate с where с.id = 1");
            System.out.println(query.uniqueResult());

            Query query = session.createQuery("from Candidate s where s.name = :fName");
            query.setParameter("fName", "Alex");
            System.out.println(query.getResultList());

            session.createQuery("update Candidate s set s.experience = :newExperience," +
                            " s.salary = :newSalary where s.id = :fId")
                    .setParameter("newExperience", "Middle Java")
                    .setParameter("newSalary", 140000)
                    .setParameter("fId", 1)
                    .executeUpdate();

            session.createQuery("delete from Candidate c where c.name = :fName")
                    .setParameter("fName", "Mihail")
                    .executeUpdate();

            session.createQuery("insert into Candidate (name, experience, salary) "
                            + "select s.name, s.experience, s.salary+100000  "
                            + "from Candidate s where s.id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();
            */

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
