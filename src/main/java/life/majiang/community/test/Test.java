package life.majiang.community.model;

import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;

public class Test
{
    public static void main(String[] args)
    {
        Animal animal = new Animal("dog");

        Person person = new Person(10, 140.0, animal);
        Person personCopy = person.clone();

        System.out.println("Before Modify:");
        System.out.println(person);
        System.out.println(personCopy.toString());

        personCopy.setAge(20);
        personCopy.setWeight(150.0);
        personCopy.setAnimalName("cat");

        System.out.println("After Modify:");
        System.out.println(person);
        System.out.println(personCopy);
    }
}
