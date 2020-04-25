package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.ReplyRepository;
import ouhk.comps380f.dao.ThreadRepository;
import ouhk.comps380f.dao.TicketUserRepository;
import ouhk.comps380f.model.Reply;
import ouhk.comps380f.model.ReplyAttachment;
import ouhk.comps380f.service.ReplyAttachmentService;
import ouhk.comps380f.service.ReplyService;
import ouhk.comps380f.service.ThreadService;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("/thread")
public class ReplyController {

    @Resource
    ThreadRepository threadRepo;

    @Resource
    TicketUserRepository userRepo;

    @Resource
    ReplyRepository replyRepo;

    @Autowired
    ThreadService threadService;

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyAttachmentService replyAttachmentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{type}/{thread_id}/createReply")
    public String saveReply(@PathVariable("type") String type, @PathVariable("thread_id") long thread_id, ReplyController.Form form, Principal principal) throws IOException {
        long id = replyService.createReply(userRepo.findById(principal.getName()).orElse(null),
                form.getContent(), threadRepo.findById(thread_id).orElse(null), form.getAttachments());
        return "redirect:/thread/" + type + "/" + thread_id;
    }

    @GetMapping("/{type}/{thread_id}/reply/{reply_id}/delete")
    public String deleteReply(@PathVariable("thread_id") long thread_id,
            @PathVariable("type") String type, @PathVariable("reply_id") long reply_id, HttpServletRequest request) {

        Reply reply = replyRepo.findById(reply_id).orElse(null);
        if (request.isUserInRole("ROLE_ADMIN") && reply != null) {
            try {
                threadService.deleteReply(reply);
            } catch (Exception e) {
            }

        }
        return "redirect:/thread/" + type + "/" + thread_id;

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{type}/{thread_id}/reply/{reply_id}/download/{attachment:.+}")
    public View download(@PathVariable("thread_id") long thread_id,
            @PathVariable("attachment") String name, @PathVariable("type") String type, @PathVariable("reply_id") long reply_id) {
        ReplyAttachment attachment = replyAttachmentService.getAttachment(reply_id, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getAttachmentName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/thread/" + type + "/" + thread_id, true);
    }

    public static class Form {

        private String content;
        private List<MultipartFile> attachments;

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
