package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.repository.UserRepository;
import xyz.qakashi.qreceipt.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
