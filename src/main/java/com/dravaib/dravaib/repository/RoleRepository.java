package com.dravaib.dravaib.repository;

import java.util.Optional;

import com.dravaib.dravaib.model.Role;
import com.dravaib.dravaib.model.RoleType;
import com.dravaib.dravaib.utils.ListUtil;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    default public Optional<Role> findByRoleType(RoleType roleType) {
        return ListUtil.iterableToList(findAll()).stream().filter(role -> role.getRoleType().equals(roleType))
                .findFirst();
    }
}
