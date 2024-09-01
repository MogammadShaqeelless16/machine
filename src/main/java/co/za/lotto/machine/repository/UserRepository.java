package co.za.lotto.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.za.lotto.machine.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Find user by name and email
    Optional<User> findByNameAndEmail(String name, String email);
}
