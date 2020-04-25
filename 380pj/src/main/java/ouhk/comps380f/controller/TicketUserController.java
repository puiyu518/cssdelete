package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.PollingResultRepository;
import ouhk.comps380f.dao.ReplyRepository;
import ouhk.comps380f.dao.ThreadRepository;
import ouhk.comps380f.dao.TicketUserRepository;
import ouhk.comps380f.exception.ReplyNotFound;
import ouhk.comps380f.model.PollingResult;
import ouhk.comps380f.model.Reply;
import ouhk.comps380f.model.TicketUser;
import ouhk.comps380f.service.ThreadService;
import ouhk.comps380f.service.TicketUserService;

@Controller
@RequestMapping("/user")

public class TicketUserController {

    @Resource
    TicketUserRepository ticketUserRepo;

    @Autowired
    TicketUserService userService;

    @Resource
    ThreadRepository threadRepo;

    @Resource
    ReplyRepository replyRepo;

    @Resource
    PollingResultRepository pollingResultRepo;

    @Autowired
    ThreadService threadService;

    @GetMapping("/")
    public String index() {
        return "redirect:/thread";
    }

    @GetMapping("/listUser")
    public String listUser(ModelMap model, HttpServletRequest request, Principal principal) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("ticketUsers", ticketUserRepo.findAll());
            model.addAttribute("edit", true);
            model.addAttribute("userForm", new Form());
            model.addAttribute("currentUser", principal.getName());

            return "listuser";
        } else {
            return "redirect:/thread";
        }

    }

    @GetMapping({"/signup", "/create"})
    public ModelAndView create(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("create");

        modelAndView.addObject("userForm", new Form());
        modelAndView.addObject("type", request.getServletPath());

        return modelAndView;
    }

    @PostMapping({"/signup", "/create"})
    public String create(Form form, HttpServletRequest request) throws IOException {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher pw = p.matcher(form.getPassword());
        Matcher un = p.matcher(form.getUsername());
        boolean bpw = pw.find();
        boolean bun = un.find();

        if (bpw || bun) {
            return "redirect:" + request.getServletPath() + "?error=spchara";

        }
        if (!form.getPassword().equals(form.getConfirm())) {
            return "redirect:" + request.getServletPath() + "?error=password";

        }
        TicketUser exists = ticketUserRepo.findById(form.getUsername()).orElse(null);
        if (exists != null) {
            return "redirect:" + request.getServletPath() + "?error=exists";

        }

        if (request.getServletPath().equals("/user/signup")) {
            TicketUser user = new TicketUser(form.getUsername(),
                    form.getPassword(), "ROLE_USER"
            );
            ticketUserRepo.save(user);
            return "redirect:/login?create";

        } else {
            if (form.getRoles().length <= 0) {
                return "redirect:" + request.getServletPath() + "?error=noRole";
            }
            TicketUser user = new TicketUser(form.getUsername(),
                    form.getPassword(), form.getRoles()
            );
            user.setDisabled(form.isDisabled());
            ticketUserRepo.save(user);
            return "redirect:/user/listUser";

        }
    }

    @PostMapping({"/{user}/edit"})
    public String edit(Form form, HttpServletRequest request, @PathVariable("user") String username) {
        if (request.isUserInRole("ROLE_ADMIN")) {

            if (!form.getPassword().equals(form.getConfirm())) {
                return "redirect:/user/listUser?error=password";

            }
            if (form.getRoles().length <= 0) {
                return "redirect:/user/listUser?error=noRole";
            }
            if (!username.equals(form.getUsername())) {
                TicketUser exists = ticketUserRepo.findById(form.getUsername()).orElse(null);
                if (exists != null) {
                    return "redirect:/user/listUser?error=exists";

                }

            }

            TicketUser user = ticketUserRepo.findById(username).orElse(null);
            user.setUsername(form.getUsername());
            user.setPassword("{noop}" + form.getPassword());
            user.setDisabled(form.isDisabled());
            user.updateRoles(form.getRoles());
            ticketUserRepo.save(user);
            return "redirect:/user/listUser";
        } else {
            return "redirect:/thread";
        }
    }

    @GetMapping("/delete/{username}")
    public View deleteTicket(@PathVariable("username") String username, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            TicketUser user = ticketUserRepo.findById(username).orElse(null);
            if (user != null) {
                List<PollingResult> pollingResult = pollingResultRepo.findByUser(user);
                for (PollingResult pr : pollingResult) {
                    try {
                        pollingResultRepo.delete(pr);
                    } catch (Exception e) {
                    }
                }

                List<Reply> replys = replyRepo.findByUser(user);
                for (Reply reply : replys) {
                    try {
                        threadService.deleteReply(reply);
                    } catch (Exception e) {
                    }
                }

                List<ouhk.comps380f.model.Thread> threads = threadRepo.findByUser(user);
                for (ouhk.comps380f.model.Thread thread : threads) {
                    threadRepo.delete(thread);
                }

                ticketUserRepo.delete(user);
            }
            return new RedirectView("/user/listUser", true);
        } else {
            return new RedirectView("/thread", true);
        }

    }

    public static class Form {

        private String username;
        private String password;
        private boolean disabled;
        private String confirm;
        private String[] roles;

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirm() {
            return confirm;
        }

        public void setConfirm(String confirm) {
            this.confirm = confirm;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

    }

}
