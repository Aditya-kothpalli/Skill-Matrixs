package com.example.demo.controller;

import com.example.demo.DTO.RoleRequestDTO;
import com.example.demo.model.RoleEntity;
import com.example.demo.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public RoleEntity createRole(@RequestBody RoleRequestDTO dto) {
        return roleService.createRoleWithSkills(dto);
    }
}
