package com.comrade.domain.ports.input.message.listener.payment;

import com.comrade.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    public void paymentCompleted(PaymentResponse paymentResponse);

    public void paymentCancelled(PaymentResponse paymentResponse);
}
