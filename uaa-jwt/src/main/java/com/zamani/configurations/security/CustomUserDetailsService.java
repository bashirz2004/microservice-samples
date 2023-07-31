package com.zamani.configurations.security;

import com.zamani.entity.MyUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository iUserRepository;

    public CustomUserDetailsService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = iUserRepository.findByUsernameEquals(username);
        if (myUser == null)
            throw new UsernameNotFoundException("User not found with the name " + username);
        return myUser;
    }

}
