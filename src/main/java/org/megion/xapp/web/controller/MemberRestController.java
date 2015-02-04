package org.megion.xapp.web.controller;

import java.util.List;

import org.megion.xapp.core.entity.Member;
import org.megion.xapp.core.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/members")
public class MemberRestController {
    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Member> listAllMembers() {
        return memberRepository.findAllOrderedByName();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Member lookupMemberById(@PathVariable("id") Long id) {
        return memberRepository.findById(id);
    }
}
