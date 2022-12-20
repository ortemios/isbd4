package org.isbd.part4.service;

import org.isbd.part4.entity.Account;
import org.isbd.part4.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return account;
    }

    public void registerUser(Account account) throws UserExistsException {
        if (accountRepository.existsByEmail(account.getUsername())) {
            throw new UserExistsException();
        }
        Account target = new Account();
        target.setEmail(account.getEmail());
        final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        target.setPassword(encoder.encode(account.getPassword()));
        accountRepository.save(target);
    }
}
