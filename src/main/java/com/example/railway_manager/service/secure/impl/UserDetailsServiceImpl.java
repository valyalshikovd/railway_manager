package com.example.railway_manager.service.secure.impl;


import com.example.railway_manager.mapper.UserMapper;
import com.example.railway_manager.model.security.UserPrincipal;
import com.example.railway_manager.model.security.Users;
import com.example.railway_manager.repository.security.UserRepository;
import com.example.railway_manager.service.secure.MyUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements MyUserDetailsService {


    private final UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        System.out.println(user.getRoles());

        return new UserPrincipal(UserMapper.toDtoWithFetching(user));
    }
}
