package dao.serviceImpl;

import dao.PersonDao;
import database.Database;
import exception.StackOverflowException;
import model.Person;

public class PersonDaoImpl implements PersonDao {
    @Override
    public String addPerson(Person person) {
        boolean isPersonExists = false;
        try {
            for (Person person1 : Database.persons) {
                if (person1.getEmail().equalsIgnoreCase(person.getEmail())) {
                    isPersonExists = true;
                    break;
                }
            }
            if (!isPersonExists) {
                Database.persons.add(person);
                return "Аккаунт успешно добавлен!";
            } else {
                throw new StackOverflowException("Аккаунт с такой электронной почтой уже существует!");
            }
        } catch (StackOverflowException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
