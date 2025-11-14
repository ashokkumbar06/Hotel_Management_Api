package com.HMA.Service;

import com.HMA.Entity.User;
import com.HMA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(String id, User user) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());

        return userRepository.save(existing);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
