package life.majiang.community.model;

import lombok.Data;

@Data
public class Person implements Cloneable{
    private int age;
    private Double weight;
    private Animal animal;

    public Person(int age, Double weight, Animal animal)
    {
        this.age = age;
        this.weight = weight;
        this.animal = animal;
    }

    @Override
    public Person clone()
    {
        try
        {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Person) super.clone();
        } catch (CloneNotSupportedException e)
        {
            throw new AssertionError();
        }
    }

    public void setAnimalName(String name)
    {
        animal.setName(name);
    }
}


