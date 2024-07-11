package com.example.boot01.repository;

import com.example.boot01.dto.Member;
import com.example.boot01.dto.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        for(int i = 0; i < 10; i++) {
            Member member=Member.builder()
                    .email(i+"naver.com")
                    .password(passwordEncoder.encode("boot"))
                    .nickname("nick"+i)
                    .build();

            member.addRole(MemberRole.USER);

            if(i>=4) {
                member.addRole(MemberRole.MANAGER);
            }
            if(i>=7) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        }
    }

    //email기준으로 조회
    @Test
    public void testSelectByEmail() {
        Member member = memberRepository.getRole("4naver.com");
        log.info("member : {}", member);
        log.info("roles : {}", member.getMemberRoleList());
    }
}
