package pe.gob.munichupaca.apiservice.auth.application.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.gob.munichupaca.apiservice.auth.domain.models.entity.UserEntity;
import pe.gob.munichupaca.apiservice.auth.infrastructure.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = findUser(username);
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name())))
                .collect(Collectors.toSet());

        return new User(userEntity.getUsername(),
                userEntity.getPassword(), true, true, true, true, authorities);
    }

    public UserEntity findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user [" + username + "] not exist."));
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return findUser(userDetails.getUsername());
        }
        else {
            return null;
        }
    }
}
