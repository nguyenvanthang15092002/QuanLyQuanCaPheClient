package com.example.project_client.viewModel.Khai;

import com.example.project_client.model.User;
import com.example.project_client.repository.UserRepository;
import lombok.Getter;

public class UpdateUserViewModel {
    private final UserRepository userRepository = new UserRepository();
    @Getter
    private final CreateUserViewModel createUserViewModel = new CreateUserViewModel();
    public void updateUser(User user) throws Exception {
        userRepository.updateUser(user);
    }
}
