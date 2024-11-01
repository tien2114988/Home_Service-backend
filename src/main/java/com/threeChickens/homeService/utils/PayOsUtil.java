//package com.larkEWA.utils;
//
//import static spark.Spark.post;
//import static spark.Spark.port;
//import static spark.Spark.staticFiles;
//
//import java.nio.file.Paths;
//
//import com.lark.oapi.core.utils.Jsons;
//import com.larkEWA.dto.salaryAdvanceReq.PayOsDto;
//import com.larkEWA.entity.FeePayment;
//import com.larkEWA.repository.IFeePaymentRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import vn.payos.PayOS;
//import vn.payos.type.CheckoutResponseData;
//import vn.payos.type.ItemData;
//import vn.payos.type.PaymentData;
//import vn.payos.type.PaymentLinkData;
//
//
//@Component
//public class PayOsUtil {
//    @Value("${payOS.apiKey}")
//    private String apiKey;
//
//    @Value("${payOS.clientId}")
//    private String clientId;
//
//    @Value("${payOS.checkSumKey}")
//    private String checksumKey;
//
//    public CheckoutResponseData createPaymentLink(PayOsDto payOsDto) throws Exception {
//        PayOS payOS = new PayOS(clientId, apiKey, checksumKey);
//
//        ItemData itemData = ItemData.builder().name("Phí ứng lương").quantity(1).price(payOsDto.getParsedFee()).build();
//        PaymentData paymentData = PaymentData.builder().orderCode(System.currentTimeMillis() / 1000).amount(payOsDto.getParsedFee())
//                .description("Thanh toán phí ứng lương").returnUrl(payOsDto.getSuccessUrl()).cancelUrl(payOsDto.getCancelUrl())
//                .item(itemData).build();
//        CheckoutResponseData result = payOS.createPaymentLink(paymentData);
//
//        return result;
//    }
//
//    public String getPaymentLinkData(Long orderCode) throws Exception {
//        PayOS payOS = new PayOS(clientId, apiKey, checksumKey);
//
//        return payOS.getPaymentLinkInformation(orderCode).getStatus();
//    }
//
//
//}
