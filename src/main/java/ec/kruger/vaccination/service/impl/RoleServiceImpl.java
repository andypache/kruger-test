package ec.kruger.vaccination.service.impl;

import ec.kruger.vaccination.domain.security.Role;
import ec.kruger.vaccination.repository.RoleRepository;
import ec.kruger.vaccination.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author andres.pacheco
 * <p>
 * Service to management role in application
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> getByName(String name) {
        return roleRepository.findByName(name);
    }
}
