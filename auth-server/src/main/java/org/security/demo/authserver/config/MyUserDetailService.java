package org.security.demo.authserver.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Liu Zhongshuai
 * @description
 * @date 2020-08-07 16:31
 **/
@Service
public class MyUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        GrantedAuthority grantedAuthority = () -> "adminManager";
        //模拟一个在数据库中查询用户的操作
        UserDetails userDetails = new MyUserDetali("liuzhongshuai@itcast.cn", new BCryptPasswordEncoder().encode("liuzhongshuai123"), List.of(grantedAuthority));
        return userDetails;
    }

    /**
     * 实现了{@link UserDetails} 接口的用户实体
     */
    class MyUserDetali implements UserDetails {

        private String username;

        private String password;

        private List<GrantedAuthority> authorities;

        public MyUserDetali(String username, String password, List<GrantedAuthority> authorities) {
            this.username = username;
            this.password = password;
            this.authorities = authorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return Collections.unmodifiableCollection(authorities);
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
