package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.PollingRepository;
import ouhk.comps380f.dao.PollingResultRepository;
import ouhk.comps380f.dao.ThreadRepository;
import ouhk.comps380f.dao.TicketUserRepository;
import ouhk.comps380f.model.Polling;
import ouhk.comps380f.service.ThreadService;
import ouhk.comps380f.model.Thread;
import ouhk.comps380f.model.ThreadAttachment;
import ouhk.comps380f.model.TicketUser;
import ouhk.comps380f.service.ReplyService;
import ouhk.comps380f.service.ThreadAttachmentService;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("/thread")
public class ThreadController {

    @Resource
    ThreadRepository threadRepo;

    @Resource
    TicketUserRepository userRepo;

    @Autowired
    ThreadService threadService;

    @Autowired
    ReplyService replyService;

    @Autowired
    ThreadAttachmentService threadAttachmentService;

    @Resource
    PollingRepository pollingRepo;

    @Resource
    PollingResultRepository pollingResultRepo;

    @GetMapping(value = {"", "/"})
    public String list(ModelMap model, Principal principal) {

        if (pollingRepo.existsByEnabled(true)) {
            Polling polling = pollingRepo.findByEnabled(true);
            model.addAttribute("polling", polling);
            model.addAttribute("pollingForm", new PollingResultController.Form());

            if (principal != null) {
                TicketUser user = userRepo.findById(principal.getName()).orElse(null);
                model.addAttribute("voted", pollingResultRepo.existsByUserAndPolling(user, polling));

            }
        } else {
            model.addAttribute("polling", null);
        }

        return "list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/createThread")
    public ModelAndView createThread() {
        return new ModelAndView("createThread", "threadForm", new Form());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createThread")
    public String saveThread(Form form, Principal principal) throws IOException {
        long id = threadService.createThread(userRepo.findById(principal.getName()).orElse(null),
                form.getTitle(), form.getContent(), form.getCategory(), form.getAttachments());
        return "redirect:/thread/" + form.getCategory() + "/" + id;
    }

    @GetMapping("/{type}")
    public String listThread(@PathVariable("type") String type, ModelMap model, HttpServletRequest request) {
        if (!type.equals("lecture") && !type.equals("lab") && !type.equals("other")) {
            return "redirect:/thread";
        }
        List<Thread> threads = threadRepo.findByCategory(type);

        Integer count = 0;
        for (Thread thread : threads) {
            if (thread.getUser().isDisabled() == false) {
                count++;
            }
        }
        model.addAttribute("threads", threads);
        model.addAttribute("total", count);
        model.addAttribute("type", type);

        return "listThread";

    }

    @GetMapping("/{type}/{thread_id}")
    public String thread(@PathVariable("type") String type, @PathVariable("thread_id") long thread_id, ModelMap model, HttpServletRequest request) {
        if (!type.equals("lecture") && !type.equals("lab") && !type.equals("other")) {
            return "redirect:/thread";
        }
        Thread thread = threadService.getThread(thread_id);
        if (thread == null || thread.getUser().isDisabled() == true) {
            return "redirect:/thread/" + type;
        }
        List<Thread> threads = threadRepo.findByCategory(type);
        model.addAttribute("thread", thread);
        model.addAttribute("threads", threads);
        model.addAttribute("replies", thread.getReply());
        model.addAttribute("replyForm", new ReplyController.Form());
        return "thread";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{type}/{thread_id}/download/{attachment:.+}")
    public View download(@PathVariable("thread_id") long thread_id,
            @PathVariable("attachment") String name, @PathVariable("type") String type) {
        ThreadAttachment attachment = threadAttachmentService.getAttachment(thread_id, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getAttachmentName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/thread/" + type + "/" + thread_id, true);
    }

    @GetMapping("/{type}/{thread_id}/delete")
    public String deleteThread(HttpServletRequest request, @PathVariable("thread_id") long thread_id, @PathVariable("type") String type) {
        Thread thread = threadRepo.findById(thread_id).orElse(null);

        if (request.isUserInRole("ROLE_ADMIN") && thread != null) {
            threadRepo.delete(thread);
        }

        return "redirect:/thread/" + type;
    }

    public static class Form {

        private String title;
        private String content;
        private String category;
        private List<MultipartFile> attachments;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    }
}
