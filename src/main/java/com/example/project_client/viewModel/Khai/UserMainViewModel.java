package com.example.project_client.viewModel.Khai;

import com.example.project_client.model.User;
import com.example.project_client.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
public class UserMainViewModel {
    private final UserRepository userRepository = new UserRepository();
    @Setter
    private User selectedUser;
    private List<User> users;
    public void initData(){
        this.users = userRepository.getAllUsers();
    }
    public void searchUser(String stringSearch) throws IOException {
        this.users = userRepository.getBySearch(stringSearch);
    }
    public void deleteUser() throws IOException {
        userRepository.deleteUser(this.selectedUser.getId());
        this.selectedUser = null;
    }
}
