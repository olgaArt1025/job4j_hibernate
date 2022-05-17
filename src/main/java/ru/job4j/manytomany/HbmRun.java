package ru.job4j.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = Book.of("Мир");
            session.save(one);
            Book two = Book.of("Труд");
            session.save(two);
            Book three = Book.of("Май");
            session.save(three);

            Author first = Author.of("Петров П.П.");
            first.getBooks().add(one);
            first.getBooks().add(two);
            first.getBooks().add(three);

          Author second = Author.of("Иванов И.И.");
            second.getBooks().add(two);

            session.persist(first);
            session.persist(second);

            Author author = session.get(Author.class, 1);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
