package com.example.runawaytravel.dto;

import com.example.runawaytravel.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final User user; //생성자 방 식으로 초기화해야함
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//role값
        System.out.println("CustomUserDetails.getAuthorities() 진입");
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() { //비밀번호값
        return user.getPassword();
    }

    @Override
    public String getUsername() { //유저이름
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //expire되었는지
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //lock 되었는지
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
