package com.atom.springsecuritydemo.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Atom
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.err.println(" invoke loadUserByUsername method");
        //1， 查询数据库判断用户名是否存在，如果不存在就会抛出UsernameNotFoundException 异常
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("username not found！");
        }
        System.err.println(username);
        //2，把查询书来的密码（注册时已经加密过）进行解析，或者直接把秘闻放入构造方法中
        String encodePassword = passwordEncoder.encode("123");
        User user = new User(username, encodePassword, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal"));
        return user;
    }
}
