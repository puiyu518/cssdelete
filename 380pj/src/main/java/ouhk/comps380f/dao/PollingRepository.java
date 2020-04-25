package ouhk.comps380f.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Polling;

public interface PollingRepository extends JpaRepository<Polling, Long> {

    public Polling findByEnabled(boolean enabled);

    public boolean existsByEnabled(boolean enabled);
}
