package ec.kruger.vaccination.service;

import ec.kruger.vaccination.domain.security.Role;

import java.util.Optional;

/**
 * @author andres.pacheco
 * <p>
 * Management service of role
 */
public interface RoleService {

    /**
     * Get rol by name
     *
     * @param name Rol name
     * @return If exist role
     */
    Optional<Role> getByName(String name);
}
