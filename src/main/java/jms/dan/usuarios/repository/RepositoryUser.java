package jms.dan.usuarios.repository;

import jms.dan.usuarios.model.User;
import jms.dan.usuarios.model.UserType;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Repository
public class RepositoryUser implements IRepositoryUser {
    private static Integer ID_GEN = 1;
    private List<UserType> userTypes = new ArrayList<>();

    @PostConstruct
    private void init() {
        UserType userType1 = new UserType(1, "CLIENT");
        userTypes.add(userType1);
        UserType userType2 = new UserType(2, "EMPLOYEE");
        userTypes.add(userType2);
    }

    @Override
    public User createUser(User newUser, UserType userType) {
        return new User(ID_GEN++, newUser.getUser(), newUser.getPassword(), newUser.getUserType());
    }

    @Override
    public List<UserType> getUserTypes() {
        return userTypes;
    }

    @Override
    public Boolean isValidUserTypeId(Integer userTypeId) {
        OptionalInt indexOpt = IntStream.range(0, userTypes.size()).filter(i -> userTypes.get(i).getId().equals(userTypeId)).findFirst();
        return indexOpt.isPresent();
    }
}
