package co.za.lotto.machine.repository;

import co.za.lotto.machine.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
