package org.dsi.blogapp.service;


import org.dsi.blogapp.exception.ResourceNotFoundException;
import org.dsi.blogapp.model.User;
import org.dsi.blogapp.payload.UserDto;
import org.dsi.blogapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto createUser(UserDto userDto) {
        return userToUserDto(userRepository.save(userDtoToUser(userDto)));
    }

    public UserDto updateUser(UserDto userDto, int userDtoId) {
        User user = userRepository.findById(userDtoId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDtoId));

        user.setName(userDto.getName());

        user.setEmail(userDto.getEmail());

        user.setAbout(userDto.getAbout());

        user.setPassword(userDto.getPassword());

        return userToUserDto(userRepository.save(user));
    }

    public UserDto getUserById(int userDtoId) {
        return userToUserDto(userRepository.findById(userDtoId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userDtoId)));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::userToUserDto)
                .collect(Collectors.toList());
    }

    public void hardDeleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);
    }

    public User userDtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto userToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
