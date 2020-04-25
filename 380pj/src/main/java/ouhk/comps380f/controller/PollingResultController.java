package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ouhk.comps380f.dao.PollingRepository;
import ouhk.comps380f.dao.PollingResultRepository;
import ouhk.comps380f.dao.TicketUserRepository;
import ouhk.comps380f.model.PollingResult;

@Controller
@RequestMapping("/pollingResult")

public class PollingResultController {

    @Resource
    PollingResultRepository pollingResultRepo;

    @Resource
    PollingRepository pollingRepo;

    @Resource
    TicketUserRepository ticketUserRepo;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{polling_id}/vote")
    public String saveResult(Form form, Principal principal, @PathVariable("polling_id") long polling_id) throws IOException {

        pollingResultRepo.save(new PollingResult(form.getOptionItem(),
                ticketUserRepo.findById(principal.getName()).orElse(null),
                pollingRepo.findById(polling_id).orElse(null)));
        return "redirect:/thread";
    }

    public static class Form {

        private Integer optionItem;

        public Integer getOptionItem() {
            return optionItem;
        }

        public void setOptionItem(Integer optionItem) {
            this.optionItem = optionItem;
        }

    }
}
