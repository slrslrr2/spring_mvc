package hello.servlet.basic.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    // Test실행 후 무조건 실행
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        Member member = new Member("gbitkim", 30);
        memberRepository.save(member);

        Member findMember = memberRepository.findId(member.getId());
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findAll(){
        Member member = new Member("gbitkim", 30);
        Member member2 = new Member("ebitkim", 30);

        memberRepository.save(member);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member, member2);
    }
}