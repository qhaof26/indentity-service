package com.tutorial.identity.repository;

import com.tutorial.identity.entity.User;
import com.tutorial.identity.repository.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SearchRepository {
    private final UserRepository userRepository;

    public Page<User> findUsers(int pageNo, int pageSize, String firstName, String lastName, String username){
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        Specification<User> specification = UserSpecification.findUsers(firstName, lastName, username);
        return userRepository.findAll(specification, pageable);
    }
}
