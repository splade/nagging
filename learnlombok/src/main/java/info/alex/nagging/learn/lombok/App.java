package info.alex.nagging.learn.lombok;

import info.alex.nagging.learn.lombok.model.Person;

public class App {

    public static void main(String[] args) {
        Person person = new Person("100", "xiaoming", "xiaoming-100", 100L);
        System.out.println(person);
    }
}
