package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.RoleRepository;
import com.onee.gestionportefeuilles.entities.Role;
import com.onee.gestionportefeuilles.service.ComptesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class RolesRestController {
    @Autowired
    ComptesService comptesService;
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/roles-par-nomRole/{nomRole}")
    public Role RolesParNomRole(@PathVariable("nomRole") String nomRole)
    {
        return comptesService.findRole(nomRole);
    }
    @GetMapping("tousLesRoles")
    public List<Role> tousLesRoles()
    {
        return roleRepository.findAll();
    }
}
