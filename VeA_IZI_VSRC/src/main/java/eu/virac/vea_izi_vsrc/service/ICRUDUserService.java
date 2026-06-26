package eu.virac.vea_izi_vsrc.service;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.User;

import java.util.ArrayList;

public interface ICRUDUserService {
    public abstract User createNewUser(User user) throws Exception;

    public abstract ArrayList<User> getAllUsers()throws Exception;

    public abstract User getUserById(long idUser)throws Exception;

    public abstract User updateUser(long idUser, String name, String surname, String email, String role, Department department)throws Exception;

    public abstract void deleteUser(long idUser)throws Exception;
}
