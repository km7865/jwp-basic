package next.service;

import core.annotation.Inject;
import core.annotation.Service;
import next.model.User;
import next.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public List<User> findAll() throws SQLException {
        return userRepository.findAll();
    }
}
