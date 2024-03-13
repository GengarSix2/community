package life.majiang.community.test;

import lombok.Data;

@Data
public class Animal
{
    private String name;

    public Animal(String name)
    {
        this.name = name;
    }
}
