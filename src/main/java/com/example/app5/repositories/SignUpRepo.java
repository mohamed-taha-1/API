package com.example.app5.repositories;

import com.example.app5.models.SignUPEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpRepo  extends PagingAndSortingRepository<SignUPEntitiy, Integer> {
    SignUPEntitiy findAllByEmail(String email);
    SignUPEntitiy findAllByUserId(String userId);

}
