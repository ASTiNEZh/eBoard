package ru.astinezh.userscrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astinezh.userscrud.entity.User;
import ru.astinezh.userscrud.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(UUID uuid) {
        User user = userRepository.findById(uuid).orElse(null);
        if (user == null)
            return user;
        else if (!user.isDeleted())
            return user;
        else
            return null;
    }

    public void create(User user) {
        if (!userRepository.existsById(user.getUuid()))
            userRepository.save(user);
    }

    public void update(UUID uuid, User user) {
        if (userRepository.findById(uuid).isPresent())
            userRepository.save(user);
    }

    public void delete(UUID uuid) {
        User user = findById(uuid);
        if (user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }
}
