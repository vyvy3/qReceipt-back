package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.config.exception.NotFoundException;
import xyz.qakashi.qreceipt.domain.User;
import xyz.qakashi.qreceipt.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(username).orElse(null);
        if (isNull(user)){
            throw NotFoundException.userNotFoundByEmail(username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), authorities);

    }
}