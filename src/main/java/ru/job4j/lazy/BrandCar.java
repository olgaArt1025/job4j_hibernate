package ru.job4j.lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brandcars")
public class BrandCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @OneToMany(mappedBy = "brand")
    private List<ModelCar> modelCars = new ArrayList<>();

    public static BrandCar of(String name) {
        BrandCar brandCar = new BrandCar();
        brandCar.name = name;
        return brandCar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModelCar> getModelCars() {
        return modelCars;
    }

    public void setModelCars(List<ModelCar> modelCars) {
        this.modelCars = modelCars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BrandCar brandCar = (BrandCar) o;
        return id == brandCar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Brand{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
