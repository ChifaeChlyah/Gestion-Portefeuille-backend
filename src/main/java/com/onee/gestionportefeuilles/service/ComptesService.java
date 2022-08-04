package com.onee.gestionportefeuilles.service;

import com.onee.gestionportefeuilles.entities.Ressource;
import com.onee.gestionportefeuilles.entities.Role;

public interface ComptesService {
    public Ressource saveUser(Ressource user);
    public Role saveRole(Role role);
    public void addRoleToUser(String email,String role);
    public Ressource findUserByEmail(String email);
    public Role findRole(String nomRole);
}
