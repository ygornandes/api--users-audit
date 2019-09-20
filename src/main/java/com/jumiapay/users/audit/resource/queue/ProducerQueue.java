package com.jumiapay.users.audit.resource.queue;

import com.jumiapay.users.audit.domain.model.Audit;

public interface ProducerQueue {
    void send(Audit audit);
}
