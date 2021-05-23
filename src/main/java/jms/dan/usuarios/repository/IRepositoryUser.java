package jms.dan.usuarios.repository;

import jms.dan.usuarios.domain.User;
import jms.dan.usuarios.domain.UserType;
import java.util.List;

public interface IRepositoryUser {
    User createUser(User newUser, UserType userType);
    List<UserType> getUserTypes();
    Boolean isValidUserTypeId(Integer userTypeId);
}
