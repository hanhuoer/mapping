package club.scoder.app.mapping.server.service;

import club.scoder.app.mapping.server.context.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getOptionalByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("username: %s not found.", username)));
    }

}
