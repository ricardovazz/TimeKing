package com.example.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.example.user.model.User;
import com.example.user.dto.UserDto;

import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = userRepository.save(new User(userDto));
        return new UserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Integer id){
        return new UserDto(userRepository.findById(id).get());
    }

    @Override
    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto){
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException());

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setSalary(userDto.getSalary());
        user.setRole(userDto.getRole());

        userRepository.save(user);

        return new UserDto(user);
    }
}
