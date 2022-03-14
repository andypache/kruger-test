package ec.kruger.vaccination.service.impl;

import ec.kruger.vaccination.domain.security.Role;
import ec.kruger.vaccination.domain.security.User;
import ec.kruger.vaccination.repository.UserRepository;
import ec.kruger.vaccination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author andres.pacheco
 * <p>
 * Service to management request for user, oauth2 and tokens
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Bad credentials: " + username);
        }
        List<GrantedAuthority> authorities = buildAuthorities(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    /**
     * Create list role user from database
     *
     * @param roles List role user
     * @return New Authorities for user
     */
    private List<GrantedAuthority> buildAuthorities(Set<Role> roles) {
        Set<GrantedAuthority> auths = new HashSet<>();
        roles.forEach((role) -> auths.add(new SimpleGrantedAuthority(role.getName())));
        return new ArrayList<>(auths);
    }
}
