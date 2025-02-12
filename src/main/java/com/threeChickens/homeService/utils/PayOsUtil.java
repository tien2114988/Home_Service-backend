package com.threeChickens.homeService.utils;

import com.threeChickens.homeService.dto.payment.PayOsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.payos.PayOS;
import vn.payos.exception.PayOSException;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;


@Component
public class PayOsUtil {
    @Value("${payOS.apiKey}")
    private String apiKey;

    @Value("${payOS.clientId}")
    private String clientId;

    @Value("${payOS.checkSumKey}")
    private String checksumKey;

    public CheckoutResponseData createPaymentLink(PayOsDto payOsDto) throws Exception {
        PayOS payOS = new PayOS(clientId, apiKey, checksumKey);

        ItemData itemData = ItemData.builder().name("Nạp tiền Home Service").quantity(1).price(payOsDto.getAmount()).build();
        PaymentData paymentData = PaymentData.builder().orderCode(System.currentTimeMillis() / 1000).amount(payOsDto.getAmount())
                .description("Nạp tiền Home Service").returnUrl(payOsDto.getSuccessUrl()).cancelUrl(payOsDto.getCancelUrl())
                .item(itemData).build();

        try {
            return payOS.createPaymentLink(paymentData);
        }catch (PayOSException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPaymentLinkData(Long orderCode) throws Exception {
        PayOS payOS = new PayOS(clientId, apiKey, checksumKey);

        return payOS.getPaymentLinkInformation(orderCode).getStatus();
    }


}
