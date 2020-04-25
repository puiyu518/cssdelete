package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.dao.ReplyAttachmentRepository;
import ouhk.comps380f.dao.ReplyRepository;
import ouhk.comps380f.exception.ReplyAttachmentNotFound;
import ouhk.comps380f.exception.ReplyNotFound;
import ouhk.comps380f.model.Reply;
import ouhk.comps380f.model.ReplyAttachment;
import ouhk.comps380f.model.Thread;
import ouhk.comps380f.model.TicketUser;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Resource
    private ReplyRepository replyRepo;

    @Resource
    private ReplyAttachmentRepository replyAttachmentRepo;

    @Override
    @Transactional
    public List<Reply> getReplies() {
        return replyRepo.findAll();
    }

    @Override
    @Transactional
    public Reply getReply(long id) {
        return replyRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = ReplyNotFound.class)
    public void delete(long id) throws ReplyNotFound {
        Reply deletedReply = replyRepo.findById(id).orElse(null);
        if (deletedReply == null) {
            throw new ReplyNotFound();
        }
        replyRepo.delete(deletedReply);
    }

    @Override
    @Transactional(rollbackFor = ReplyAttachmentNotFound.class)
    public void deleteAttachment(long id, String attachmentName) throws ReplyAttachmentNotFound {
        Reply reply = replyRepo.findById(id).orElse(null);
        for (ReplyAttachment attachment : reply.getAttachments()) {
            if (attachment.getAttachmentName().equals(attachmentName)) {
                reply.deleteAttachment(attachment);
                replyRepo.save(reply);
                return;
            }
        }
        throw new ReplyAttachmentNotFound();
    }

    @Override
    @Transactional
    public long createReply(TicketUser user, String content, Thread thread, List<MultipartFile> attachments) throws IOException {
        Reply reply = new Reply();
        reply.setUser(user);
        reply.setThread(thread);
        reply.setContent(content);
        reply.setCreated_at(new Date());
        for (MultipartFile filePart : attachments) {
            ReplyAttachment attachment = new ReplyAttachment();
            attachment.setAttachmentName(filePart.getOriginalFilename().replaceAll("[^a-zA-Z0-9_.]", ""));
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setReply(reply);
            if (attachment.getAttachmentName() != null && attachment.getAttachmentName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                reply.getAttachments().add(attachment);
            }
        }
        Reply savedReply = replyRepo.save(reply);
        return savedReply.getId();
    }

    @Override
    @Transactional(rollbackFor = ReplyNotFound.class)
    public void updateReply(long id, String content, Thread thread, List<MultipartFile> attachments)
            throws IOException, ReplyNotFound {
        Reply updatedReply = replyRepo.findById(id).orElse(null);
        if (updatedReply == null) {
            throw new ReplyNotFound();
        }
        updatedReply.setContent(content);

        for (MultipartFile filePart : attachments) {
            ReplyAttachment attachment = new ReplyAttachment();
            attachment.setAttachmentName(filePart.getOriginalFilename().replaceAll("[^a-zA-Z0-9_.]", ""));
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setReply(updatedReply);
            if (attachment.getAttachmentName() != null && attachment.getAttachmentName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedReply.getAttachments().add(attachment);
            }
        }
        replyRepo.save(updatedReply);
    }
}
