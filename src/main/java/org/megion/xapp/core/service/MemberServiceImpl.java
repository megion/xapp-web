package org.megion.xapp.core.service;

import org.megion.xapp.core.entity.Member;
import org.megion.xapp.core.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	@Transactional
	public void register(Member member) {
		memberRepository.register(member);
	}

}
