package ru.astinezh.userscrud.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ASTiNEZh.dto.UserDTO;
import ru.astinezh.userscrud.entity.User;
import ru.astinezh.userscrud.repository.UserRepository;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO findById(UUID uuid) {
        User user = userRepository.findById(uuid).orElse(null);
        if (Objects.nonNull(user) && !user.isDeleted())
            return modelMapper.map(user, UserDTO.class);
        else
            return null;
    }

    public void create(UserDTO userDTO) {
        if (!userRepository.existsById(userDTO.getUuid()))
            userRepository.save(modelMapper.map(userDTO, User.class));
    }

    public void update(UUID uuid, UserDTO userDTO) {
        if (userRepository.findById(uuid).isPresent())
            userRepository.save(modelMapper.map(userDTO, User.class));
    }

    public void delete(UUID uuid) {
        User user = userRepository.findById(uuid).orElse(null);
        if (user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }
}
