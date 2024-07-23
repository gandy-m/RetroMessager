package messager.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import messager.model.User;
import messager.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User add(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User registration(String username, String password) throws Exception {
        if (!username.isEmpty() && !password.isEmpty()) {
            if (userRepository.findUserByUsername(username).isEmpty()) {
                User user = new User(username, passwordEncoder.encode(password));
                userRepository.save(user);
                return user;
            }
        }
        throw new Exception();
    }

    @Transactional
    @Override
    public User find(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(username);
    }


    @Transactional
    public void setImage(MultipartFile file, String username) throws IOException {
        User user;
        if (userRepository.findUserByUsername(username).isPresent()) {
            user = userRepository.findUserByUsername(username).get();
            user.setImage(file.getBytes());
            userRepository.save(user);
        }
        else {
            throw new IOException();
        }
    }
}