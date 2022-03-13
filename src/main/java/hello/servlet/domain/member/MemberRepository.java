package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 싱글톤 객체로 생성
// 스프링을 사용하여 빈으로 등록하면 싱글톤으로 만들어지지만 지금은 최대한 순수 서블릿만으로 구현하는것이 목적
public class MemberRepository {
    public static Map<Long, Member> store = new HashMap<>();
    public static long sequence = 0L;

    public static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findId(Long id){
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
