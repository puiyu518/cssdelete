package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.ThreadAttachment;

public interface ThreadAttachmentRepository extends JpaRepository<ThreadAttachment, Long>{
    public ThreadAttachment findByRidAndAttachmentName(long rid, String attachmentName);
}
