package ouhk.comps380f.service;

import ouhk.comps380f.model.ThreadAttachment;


public interface ThreadAttachmentService {
    public ThreadAttachment getAttachment(long rid, String attachmentName);
}
