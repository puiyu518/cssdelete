package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.dao.ThreadAttachmentRepository;
import ouhk.comps380f.dao.ThreadRepository;
import ouhk.comps380f.exception.ReplyNotFound;
import ouhk.comps380f.exception.ThreadAttachmentNotFound;
import ouhk.comps380f.exception.ThreadNotFound;
import ouhk.comps380f.model.Reply;
import ouhk.comps380f.model.Thread;
import ouhk.comps380f.model.ThreadAttachment;
import ouhk.comps380f.model.TicketUser;

@Service
public class ThreadServiceImpl implements ThreadService {

    @Resource
    private ThreadRepository threadRepo;

    @Resource
    private ThreadAttachmentRepository threadAttachmentRepo;

    @Override
    @Transactional
    public List<Thread> getThreads() {
        return threadRepo.findAll();
    }

    @Override
    @Transactional
    public Thread getThread(long id) {
        return threadRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = ThreadNotFound.class)
    public void delete(long id) throws ThreadNotFound {
        Thread deletedThread = threadRepo.findById(id).orElse(null);
        if (deletedThread == null) {
            throw new ThreadNotFound();
        }
        threadRepo.delete(deletedThread);
    }

    @Override
    @Transactional(rollbackFor = ThreadAttachmentNotFound.class)
    public void deleteAttachment(long id, String attachmentName) throws ThreadAttachmentNotFound {
        Thread thread = threadRepo.findById(id).orElse(null);
        for (ThreadAttachment attachment : thread.getAttachments()) {
            if (attachment.getAttachmentName().equals(attachmentName)) {
                thread.deleteAttachment(attachment);
                threadRepo.save(thread);
                return;
            }
        }
        throw new ThreadAttachmentNotFound();
    }

    @Override
    @Transactional
    public long createThread(TicketUser user, String title, String content, String category, List<MultipartFile> attachments)
            throws IOException {
        Thread thread = new Thread();
        thread.setUser(user);
        thread.setTitle(title);
        thread.setCategory(category);
        thread.setContent(content);
        thread.setCreated_at(new Date());
        for (MultipartFile filePart : attachments) {
            ThreadAttachment attachment = new ThreadAttachment();
            attachment.setAttachmentName(filePart.getOriginalFilename().replaceAll("[^a-zA-Z0-9_.]", ""));
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setThread(thread);
            if (attachment.getAttachmentName() != null && attachment.getAttachmentName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                thread.getAttachments().add(attachment);
            }
        }
        Thread savedThread = threadRepo.save(thread);
        return savedThread.getId();
    }

    @Override
    @Transactional(rollbackFor = ThreadNotFound.class)
    public void updateThread(long id, String title, String content, String category, List<MultipartFile> attachments)
            throws IOException, ThreadNotFound {
        Thread updatedThread = threadRepo.findById(id).orElse(null);
        if (updatedThread == null) {
            throw new ThreadNotFound();
        }
        updatedThread.setTitle(title);
        updatedThread.setContent(content);
        updatedThread.setCategory(category);

        for (MultipartFile filePart : attachments) {
            ThreadAttachment attachment = new ThreadAttachment();
            attachment.setAttachmentName(filePart.getOriginalFilename().replaceAll("[^a-zA-Z0-9_.]", ""));
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setThread(updatedThread);
            if (attachment.getAttachmentName() != null && attachment.getAttachmentName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedThread.getAttachments().add(attachment);
            }
        }
        threadRepo.save(updatedThread);
    }

    @Override
    @Transactional(rollbackFor = ReplyNotFound.class)
    public void deleteReply(Reply reply) throws ReplyNotFound {
        if (reply == null) {
            throw new ReplyNotFound();
        }
        Thread thread = reply.getThread();

        thread.getReply().remove(reply);
        threadRepo.save(thread);
    }

}
