package com.example.boot01.dto;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString(exclude = "memberRoleList")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private String email;

    private String password;
    private String nickname;
    private boolean slogin;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();



    public void addRole(MemberRole role) {
        memberRoleList.add(role);
    }


}
