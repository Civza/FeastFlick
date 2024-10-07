package com.feastflick.service;

import com.feastflick.model.User;

public interface IUserService {
    public User findUserByToken(String token) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
