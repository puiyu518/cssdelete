package ouhk.comps380f.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.ThreadAttachmentRepository;
import ouhk.comps380f.model.ThreadAttachment;

@Service
public class ThreadAttachmentServiceImpl implements ThreadAttachmentService {

    @Resource
    private ThreadAttachmentRepository threadAttachmentRepo;

    @Override
    @Transactional
    public ThreadAttachment getAttachment(long rid, String attachmentName) {
        return threadAttachmentRepo.findByRidAndAttachmentName(rid, attachmentName);
    }

}
