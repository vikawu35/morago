package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Role;
import com.habsida.moragoproject.model.input.RoleInput;
import com.habsida.moragoproject.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @QueryMapping
    public List<Role> getAllRole(){
        return roleService.findAll();
    }

    @QueryMapping
    public Page<Role> getAllRolePaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return roleService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Role getRoleById(@Argument Long id){
        return roleService.findById(id);
    }

    @MutationMapping
    public Boolean deleteRoleById(@Argument Long id){
        return roleService.delete(id);
    }

    @MutationMapping
    public Role updateRole(@Argument Long id, @Argument RoleInput roleInput){
        return roleService.update(id, roleInput);
    }
}
