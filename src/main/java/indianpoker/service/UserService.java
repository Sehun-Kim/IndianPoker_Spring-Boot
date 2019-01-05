package indianpoker.service;

import indianpoker.domain.user.Picture;
import indianpoker.domain.user.User;
import indianpoker.domain.user.UserRepository;
import indianpoker.exception.NonExistDataException;
import indianpoker.exception.UnAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;


    public User add(User user, Picture picture) {
        logger.debug("picture name : {}", picture.getFileName());
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NonExistDataException::new);
    }

    public User login(String userId, String password) throws UnAuthenticationException {
        return userRepository.findByUserId(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(UnAuthenticationException::new);
    }
}
