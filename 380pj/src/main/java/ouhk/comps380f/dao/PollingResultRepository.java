package ouhk.comps380f.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Polling;
import ouhk.comps380f.model.PollingResult;
import ouhk.comps380f.model.TicketUser;

public interface PollingResultRepository extends JpaRepository<PollingResult, Long> {

    public List<PollingResult> findByPollingAndVoted(Polling rid, Integer voted);

    public List<PollingResult> findByPolling(Polling rid);

    public boolean existsByUserAndPolling(TicketUser user, Polling polling);

    public List<PollingResult> findByUser(TicketUser user);

}
