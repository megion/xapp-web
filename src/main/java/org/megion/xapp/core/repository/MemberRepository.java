package org.megion.xapp.core.repository;

import java.util.List;

import org.megion.xapp.core.entity.Member;

public interface MemberRepository {
    public Member findById(Long id);

    public Member findByEmail(String email);

    public List<Member> findAllOrderedByName();

    public void register(Member member);
    
    void test1();
    
    void test2();
}
