package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(int id);
    List<User> findAuthors();
}
