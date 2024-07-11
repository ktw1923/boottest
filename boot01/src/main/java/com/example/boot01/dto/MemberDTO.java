package com.example.boot01.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberDTO extends User {

    private String password;

    private String email;

    private String nickname;

    private boolean slogin;

    private List<String> roleName = new ArrayList<>();

    public MemberDTO(String email, String password, String nickname, boolean slogin
            , List<String> roleName) {

        super(email, password, roleName.stream().map(str ->
                new SimpleGrantedAuthority("ROLE_"+str)).collect(Collectors.toList()));

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.slogin = slogin;
        this.roleName = roleName;

    }
}
