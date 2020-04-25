package ouhk.comps380f.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Reply;
import ouhk.comps380f.model.TicketUser;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
        public List<Reply> findByUser(TicketUser user);

}
