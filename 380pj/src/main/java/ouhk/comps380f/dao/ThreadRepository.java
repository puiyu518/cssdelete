package ouhk.comps380f.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Thread;
import ouhk.comps380f.model.TicketUser;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

    public List<Thread> findByCategory(String category);
    public List<Thread> findByUser(TicketUser user);
}
