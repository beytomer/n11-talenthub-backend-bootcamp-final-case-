package com.n11.userreviewservice.repository;

import com.n11.userreviewservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author BeytullahBilek
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
