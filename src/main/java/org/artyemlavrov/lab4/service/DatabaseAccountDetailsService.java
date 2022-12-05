package org.artyemlavrov.lab4.service;

import org.artyemlavrov.lab4.entity.Account;
import org.artyemlavrov.lab4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseAccountDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return account;
    }

    public void registerUser(Account account) throws UserExistsException {
        if (userRepository.existsByUsername(account.getUsername())) {
            throw new UserExistsException();
        }
        Account target = new Account();
        target.setUsername(account.getUsername());
        final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        target.setPassword(encoder.encode(account.getPassword()));
        userRepository.save(target);
    }
}
