package indianpoker.service;

import indianpoker.domain.user.User;
import indianpoker.domain.user.UserRepository;
import indianpoker.exception.NonExistDataException;
import indianpoker.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User user) {
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NonExistDataException::new);
    }

    public User login(String userId, String password) throws UnAuthenticationException {
        return userRepository.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(UnAuthenticationException::new)
                .toPlayer(); // login이 성공하면 Player의 자격이 주어짐
    }
}
