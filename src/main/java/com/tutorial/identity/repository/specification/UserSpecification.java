package com.tutorial.identity.repository.specification;

import com.tutorial.identity.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> findUsers(String firstName, String lastName, String username) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

//            if (genreName != null && !genreName.isEmpty()) {
//                predicates.add(cb.equal(root.join("genres").get("name"), genreName));
//            }

            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));
            }
            if (lastName != null && !lastName.isEmpty()) {
                predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));
            }
            if (username != null && !username.isEmpty()) {
                predicates.add(cb.like(root.get("username"), "%" + username + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}