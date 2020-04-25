package ouhk.comps380f.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.ReplyAttachmentRepository;
import ouhk.comps380f.model.ReplyAttachment;

@Service
public class ReplyAttachmentServiceImpl implements ReplyAttachmentService {

    @Resource
    private ReplyAttachmentRepository replyAttachmentRepo;

    @Override
    @Transactional
    public ReplyAttachment getAttachment(long rid, String attachmentName){
        return replyAttachmentRepo.findByRidAndAttachmentName(rid, attachmentName);
    }

}
