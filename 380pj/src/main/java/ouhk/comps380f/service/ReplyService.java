package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.exception.ReplyAttachmentNotFound;
import ouhk.comps380f.exception.ReplyNotFound;
import ouhk.comps380f.model.Reply;
import ouhk.comps380f.model.TicketUser;
import ouhk.comps380f.model.Thread;

public interface ReplyService
{

    public long createReply(TicketUser user, String content, Thread thread, List<MultipartFile> attachments) throws IOException;

    public List<Reply> getReplies();

    public Reply getReply(long id);

    public void updateReply(long id, String content, Thread thread, List<MultipartFile> attachments)
            throws IOException, ReplyNotFound;

    public void delete(long id) throws ReplyNotFound;

    public void deleteAttachment(long id, String attachmentName)
            throws ReplyAttachmentNotFound;
}
