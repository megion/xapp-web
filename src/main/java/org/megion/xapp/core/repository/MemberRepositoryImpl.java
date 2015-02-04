package org.megion.xapp.core.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.megion.xapp.core.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    @Autowired
    private EntityManager em;
    
    @Autowired 
    private ApplicationContext applicationContext;
    
    private MemberRepository getSelf() {
    	return (MemberRepository)applicationContext.getBean(MemberRepository.class);
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);

        /*
         * Swap criteria statements if you would like to try out type-safe criteria queries, a new
         * feature in JPA 2.0 criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
         */

        criteria.select(member).where(cb.equal(member.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Member> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);

        /*
         * Swap criteria statements if you would like to try out type-safe criteria queries, a new
         * feature in JPA 2.0 criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
         */

        criteria.select(member).orderBy(cb.asc(member.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public void register(Member member) {
        em.persist(member);
        
        /*int a = 10;
    	if (a==10) {
			throw new IllegalArgumentException("throw test error");
    	}*/
        return;
    }

	@Override
	public void test1() {
    	MemberRepository self = getSelf();
    	for (int i=0; i<3; i++) {
    		self.test2();
		}
	}

	@Override
	public void test2() {
    	MemberRepository self = getSelf();
		List<Member> ms = self.findAllOrderedByName();
		System.out.println("ms: " + ms);
	}
}
