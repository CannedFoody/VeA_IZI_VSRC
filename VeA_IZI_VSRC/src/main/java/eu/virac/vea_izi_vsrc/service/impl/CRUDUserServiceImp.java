package eu.virac.vea_izi_vsrc.service.impl;

import eu.virac.vea_izi_vsrc.model.Department;
import eu.virac.vea_izi_vsrc.model.User;
import eu.virac.vea_izi_vsrc.repo.IDepartmentRepo;
import eu.virac.vea_izi_vsrc.repo.IUserRepo;
import eu.virac.vea_izi_vsrc.service.ICRUDUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CRUDUserServiceImp implements ICRUDUserService {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private IDepartmentRepo departmentRepo;

    @Override
    public User createNewUser(User user) throws Exception {
        if(user == null){
            throw new Exception("Passed User object is null...");
        }

        if(userRepo.existsById(user.getIdUser())){
            throw new Exception("This user already exists...");
        }

        if(user.getName().isEmpty() || user.getSurname().isEmpty() || user.getEmail().isEmpty() || user.getRole().isEmpty() || user.getDepartment() == null){
            throw new Exception("One of the user fields is empty or null...");
        }

        if(!departmentRepo.existsById(user.getDepartment().getIdDepartment())){
            throw new Exception("The department linked to the passed User object doesnt exist...");
        }

        return userRepo.save(user);
    }

    @Override
    public ArrayList<User> getAllUsers() throws Exception {
        if(userRepo.count() == 0){
            throw new Exception("User repo is empty...");
        }

        ArrayList<User> returned_list = (ArrayList<User>) userRepo.findAll();
        return returned_list;
    }

    @Override
    public User getUserById(long idUser) throws Exception {
        if(userRepo.count() == 0){
            throw new Exception("User repo is empty...");
        }

        if(idUser < 1){
            throw new Exception("Invalid user ID passed...");
        }

        if(!userRepo.existsById(idUser)){
            throw new Exception("No user exists by that ID...");
        }

        return userRepo.findById(idUser).get();
    }

    @Override
    public User updateUser(long idUser, String name, String surname, String email, String role, Department department) throws Exception {
        if(!userRepo.existsById(idUser)){
            throw new Exception("No user exists with that ID...");
        }

        if(idUser < 1){
            throw new Exception("Invalid ID passed...");
        }

        if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || role.isEmpty() || department == null){
            throw new Exception("Incorrect data passed for updating...");
        }

        if(!departmentRepo.existsById(department.getIdDepartment())){
            throw new Exception("The department linked to the passed User object doesnt exist...");
        }

        User user_to_update = userRepo.findById(idUser).get();

        if(!user_to_update.getName().equals(name)){
            user_to_update.setName(name);
        }

        if(!user_to_update.getSurname().equals(surname)){
            user_to_update.setSurname(surname);
        }

        if(!user_to_update.getEmail().equals(email)){
            user_to_update.setEmail(email);
        }

        if(!user_to_update.getRole().equals(role)){
            user_to_update.setRole(role);
        }

        if(user_to_update.getDepartment() != department){
            user_to_update.setDepartment(department);
        }

        return userRepo.save(user_to_update);
    }

    @Override
    public void deleteUser(long idUser) throws Exception {
        if(idUser < 1){
            throw new Exception("Invalid user ID passed...");
        }

        if(userRepo.existsById(idUser)){
            userRepo.deleteById(idUser);
        }
        else{
            throw new Exception("No User exists by that ID...");
        }
    }
}
