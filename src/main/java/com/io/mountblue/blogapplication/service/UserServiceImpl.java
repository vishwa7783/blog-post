package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.UserRepository;
import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(int id) {
        Optional<User> result = userRepository.findById(id);
        User user = null;

        if (result.isPresent()) {
            user = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
        }
        return user;
    }

    @Override
    public List<User> findAuthors() {
        return userRepository.findAll();
    }
}
