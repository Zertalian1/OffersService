package com.example.service;

import com.example.domain.dto.RegistrationDto;
import com.example.domain.dto.ShareUserDto;
import com.example.domain.entity.security.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataSender<ShareUserDto> sender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Попытка найти пользователя  "+ username);
        return userRepository.findUsersByUsername(username).orElseThrow(
                () -> {
                    System.out.println("пользователь не найден");
                    return new UsernameNotFoundException("Пользователь не найден");
                }
        );
    }

    public void registrationUser(RegistrationDto registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userRepository.save(user);
        ShareUserDto shareUser = new ShareUserDto(
                registrationRequest.getUsername(),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getPatronymic()
        );
        sender.sendMessage(shareUser);
    }
}
