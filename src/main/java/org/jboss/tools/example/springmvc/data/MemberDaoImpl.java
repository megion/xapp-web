/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.tools.example.springmvc.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.tools.example.springmvc.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@Service
@Transactional
public class MemberDaoImpl implements MemberDao {
    @Autowired
    private EntityManager em;
    
    @Autowired 
    private ApplicationContext applicationContext;
    
    private MemberDao getSelf() {
    	return (MemberDao)applicationContext.getBean(MemberDao.class);
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

    @Transactional(noRollbackFor=IllegalArgumentException.class)
    public void register(Member member) {
        em.persist(member);
        
        int a = 10;
    	if (a==10) {
			throw new IllegalArgumentException("throw test error");
    	}
        return;
    }

    @Transactional(propagation=Propagation.NEVER)
	@Override
	public void test1() {
    	MemberDao self = getSelf();
    	for (int i=0; i<3; i++) {
    		self.test2();
		}
	}

    @Transactional
	@Override
	public void test2() {
    	MemberDao self = getSelf();
		List<Member> ms = self.findAllOrderedByName();
		System.out.println("ms: " + ms);
	}
}
