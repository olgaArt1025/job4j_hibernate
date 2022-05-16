package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.many.model.Brand;
import ru.job4j.many.model.Model;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Model one = Model.of("Passat");
            session.save(one);
            Model two = Model.of("Golf");
            session.save(two);
            Model three = Model.of("Polo");
            session.save(three);
            Model  four  = Model.of("Tiguan");
            session.save(four);
            Model  five  = Model.of("Touareg");
            session.save(five);

            Brand car = Brand.of("Volkswagen");
            car.addModel(session.load(Model.class, 1));
            car.addModel(session.load(Model.class, 2));
            car.addModel(session.load(Model.class, 3));
            car.addModel(session.load(Model.class, 4));
            car.addModel(session.load(Model.class, 5));

            session.save(car);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
