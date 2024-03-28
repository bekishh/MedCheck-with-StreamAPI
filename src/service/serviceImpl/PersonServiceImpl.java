package service.serviceImpl;

import dao.PersonDao;
import dao.serviceImpl.PersonDaoImpl;
import model.Person;
import service.PersonService;

public class PersonServiceImpl implements PersonService {
    PersonDao personDao = new PersonDaoImpl();
    @Override
    public String addPerson(Person person) {
        return personDao.addPerson(person);
    }
}
