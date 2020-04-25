package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.exception.ReplyNotFound;
import ouhk.comps380f.exception.ThreadAttachmentNotFound;
import ouhk.comps380f.exception.ThreadNotFound;
import ouhk.comps380f.model.Reply;
import ouhk.comps380f.model.TicketUser;
import ouhk.comps380f.model.Thread;

public interface ThreadService {

    public long createThread(TicketUser user, String title, String content, String category, List<MultipartFile> attachments) throws IOException;

    public List<Thread> getThreads();

    public Thread getThread(long id);

    public void updateThread(long id,  String title, String content, String category, List<MultipartFile> attachments)
            throws IOException, ThreadNotFound;

    public void delete(long id) throws ThreadNotFound;

    public void deleteAttachment(long id, String attachmentName)
            throws ThreadAttachmentNotFound;
    
      public void deleteReply(Reply reply) throws ReplyNotFound;
}
