package com.onee.gestionportefeuilles.service;

import com.onee.gestionportefeuilles.dao.RessourceRepository;
import com.onee.gestionportefeuilles.entities.Ressource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    ComptesService comptesService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Ressource user= comptesService.findUserByEmail(email);
        if(user==null) throw new UsernameNotFoundException(email);
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getNomRole()));
        });
        return new User(user.getEmail(),user.getPassword(),authorities);
    }
}
