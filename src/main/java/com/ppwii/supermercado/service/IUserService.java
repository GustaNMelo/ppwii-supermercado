package com.ppwii.supermercado.service;

import java.util.List;

import com.ppwii.supermercado.model.User;

public interface IUserService {

	public Integer saveUser(User user);

	public List<User> getAllUsers();

	public void deleteUserById(Integer id);
}
