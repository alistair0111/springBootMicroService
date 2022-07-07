package com.microservice.ewallet.user.repository;


import com.microservice.ewallet.user.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository  extends JpaRepository<MyUser, Integer> {
}
