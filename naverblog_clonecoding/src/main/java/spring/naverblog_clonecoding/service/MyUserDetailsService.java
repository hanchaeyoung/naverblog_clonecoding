package spring.naverblog_clonecoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.naverblog_clonecoding.domain.MyUserDetails;
import spring.naverblog_clonecoding.domain.User;
import spring.naverblog_clonecoding.repository.BoardRepository;
import spring.naverblog_clonecoding.repository.UserRepository;

@Service(value = "userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    private BoardRepository boardRepository;
    private UserRepository userRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            User user = userService.read(username);
            return new MyUserDetails(user);
        } catch (IllegalArgumentException e) {
            throw new UsernameNotFoundException(username);
        }
    }
}
