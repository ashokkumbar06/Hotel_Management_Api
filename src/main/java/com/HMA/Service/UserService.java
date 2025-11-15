package com.HMA.Service;

import com.HMA.Entity.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(String id);

    User updateUser(String id, User user);

    void deleteUser(String id);
}
