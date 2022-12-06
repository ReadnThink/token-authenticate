package com.token.authenticate.repository;

import com.token.authenticate.domain.User;
import net.bytebuddy.description.NamedElement;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
