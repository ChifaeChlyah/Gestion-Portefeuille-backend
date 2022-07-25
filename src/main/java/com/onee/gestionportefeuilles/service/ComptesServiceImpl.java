package com.onee.gestionportefeuilles.service;

import com.onee.gestionportefeuilles.dao.RessourceRepository;
import com.onee.gestionportefeuilles.dao.RoleRepository;
import com.onee.gestionportefeuilles.entities.Ressource;
import com.onee.gestionportefeuilles.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ComptesServiceImpl implements ComptesService{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RessourceRepository ressourceRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Ressource saveUser(Ressource user) {
        String hashPassword=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return ressourceRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String nomRole) {
        Role role=roleRepository.findByNomRole(nomRole).get(0);
        Ressource user=ressourceRepository.findByEmail(email).get(0);
        user.getRoles().add(role);
    }

    @Override
    public Ressource findUserByEmail(String email) {
        return ressourceRepository.findByEmail(email).get(0);
    }
}
