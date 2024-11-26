package projeto.com.br.form.processing.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import projeto.com.br.form.processing.domain.model.user.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.dataExclusao IS NULL")
    List<User> findAllAtivos();

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.dataExclusao IS NULL")
    User findActiveByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailUser(String email);


}
