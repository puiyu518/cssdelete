package ouhk.comps380f.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.PollingRepository;
import ouhk.comps380f.model.Polling;

@Controller
@RequestMapping("/polling")
public class PollController {

    @Resource
    PollingRepository pollingRepo;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public ModelAndView createPolling(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("createPolling");
            modelAndView.addObject("pollingForm", new Form());
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/polling/list");
        }

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String savePolling(Form form) throws IOException {
        if (form.getTitle() == null) {
            return "redirect:/polling/create?error=noTitle";
        }
        Polling polling = new Polling(form.getTitle(), form.getOption1(), form.getOption2(), form.getOption3(), form.getOption4());
        pollingRepo.save(polling);
        return "redirect:/polling/list";
    }

    @GetMapping({"/list", "/"})
    public ModelAndView list(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            ModelAndView modelAndView = new ModelAndView("listPolling");
            modelAndView.addObject("pollings", pollingRepo.findAll());
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/thread");
        }

    }

    @GetMapping("/delete/{polling}")
    public View deletePolling(@PathVariable("polling") long polling, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            pollingRepo.delete(pollingRepo.findById(polling).orElse(null));
            return new RedirectView("/polling/list", true);
        } else {
            return new RedirectView("/thread", true);
        }
    }

    @GetMapping("/enable/{polling}")
    public String enablePolling(@PathVariable("polling") long polling_id, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            boolean exists = pollingRepo.existsByEnabled(true);
            if (exists) {
                return "redirect:/polling/list?error=exists";
            } else {
                Polling polling = pollingRepo.findById(polling_id).orElse(null);
                if (polling == null) {
                    return "redirect:/polling/list?error=noExists";
                }
                polling.setEnabled(true);
                pollingRepo.save(polling);
                return "redirect:/polling/list";
            }
        }
        return "redirect:/thread";
    }

    @GetMapping("/disable/{polling}")
    public String disablePolling(@PathVariable("polling") long polling_id, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            Polling polling = pollingRepo.findById(polling_id).orElse(null);
            if (polling == null) {
                return "redirect:/polling/list?error=noExists";
            }
            polling.setEnabled(false);
            pollingRepo.save(polling);
            return "redirect:/polling/list";
        }
        return "redirect:/thread";
    }

    public static class Form {

        private String title;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private boolean disabled;

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }
    }
}
