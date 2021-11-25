package com.example.user;

import java.util.List;

import com.example.user.dto.UserDto;

public interface UserService {
    public UserDto saveUser(UserDto userDto);
    public List<UserDto> getAllUsers();
    public UserDto getUser(Integer id);
    public void delete(Integer id);
    public UserDto updateUser(Integer id, UserDto userDto);
}
