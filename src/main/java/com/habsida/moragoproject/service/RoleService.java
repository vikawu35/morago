package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Role;
import com.habsida.moragoproject.model.input.RoleInput;
import com.habsida.moragoproject.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Page<Role> findAllPaged(PageRequest pageRequest) {
        return roleRepository.findAll(pageRequest);
    }

    public Role findById(Long id){
        return roleRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find Role by Id: " + id));
    }

    public Role create (Role role) {

        if (role.getName() != null) {
            Optional<Role> existingRole = roleRepository.findByName(role.getName());

            if (existingRole.isPresent()) {
                return existingRole.get();
            } else {
                role = new Role(role.getName());
                roleRepository.save(role);
            }
        }
        return role;
    }

    public Boolean delete(Long id){
        roleRepository.deleteById(id);
        return true;
    }

    public Role update(Long id, RoleInput roleInput){
        Role role = findById(id);

        if (roleInput.getName() != null){
            role.setName((roleInput.getName()));
        }
        return roleRepository.save(role);
    }
}
