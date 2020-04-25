package ouhk.comps380f.service;

import ouhk.comps380f.model.ReplyAttachment;


public interface ReplyAttachmentService {
    public ReplyAttachment getAttachment(long rid, String attachmentName);
}
