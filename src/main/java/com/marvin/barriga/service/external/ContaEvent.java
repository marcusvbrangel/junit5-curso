package com.marvin.barriga.service.external;

import com.marvin.barriga.domain.Conta;

public interface ContaEvent {
    enum EventType {
        CREATED, UPDATED, DELETED
    }

    void dispatch(Conta conta, EventType eventType);
}
