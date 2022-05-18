package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        List<BrandCar> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            BrandCar car = BrandCar.of("Volkswagen");
            session.save(car);
            BrandCar car2 = BrandCar.of("Ford");
            session.save(car2);

            ModelCar one = ModelCar.of("Passat", car);
            session.save(one);
            ModelCar two = ModelCar.of("Golf", car);
            session.save(two);
            ModelCar three = ModelCar.of("Polo", car);
            session.save(three);
            ModelCar four = ModelCar.of("Tiguan", car);
            session.save(four);
            ModelCar five = ModelCar.of("Touareg", car);
            session.save(five);
            ModelCar six = ModelCar.of("Focus", car2);
            session.save(six);
            ModelCar seven = ModelCar.of("Kuga", car2);
            session.save(seven);

            list = session.createQuery("from BrandCar").list();
            for (BrandCar brand : list) {
                for (ModelCar model : brand.getModelCars()) {
                    System.out.println(model);
                }
            }

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
