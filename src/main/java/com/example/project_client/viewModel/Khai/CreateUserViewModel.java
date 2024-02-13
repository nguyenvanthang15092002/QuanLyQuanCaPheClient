package com.example.project_client.viewModel.Khai;

import com.example.project_client.model.Staff;
import com.example.project_client.model.User;
import com.example.project_client.repository.StaffCalRepository;
import com.example.project_client.repository.UserRepository;

import java.io.IOException;
import java.util.List;

public class CreateUserViewModel {
    private final UserRepository userRepository = new UserRepository();
    private final StaffCalRepository staffCalRepository = new StaffCalRepository();
    public boolean checkUsername(String username) throws IOException {
        List<User> users = userRepository.getByUserName(username);
        return users == null || users.isEmpty();
    }
    public void createUser(User user) throws Exception {
        userRepository.addUser(user);
    }
    public boolean checkStaffId(String staffId) throws Exception {
        Staff staff = staffCalRepository.getStaffByIdApi(staffId);
        return  staff == null;
    }
}
