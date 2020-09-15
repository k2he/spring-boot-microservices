package com.demo.microservices.authservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.microservices.authservice.model.Role;
import com.demo.microservices.authservice.model.RoleName;
/**
 * @author kaihe
 *
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName name);
  
}
