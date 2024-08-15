package com.ecommerce.id.vn.paymentVNpay;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paymentVNpay")
public class PaymentVNpayController {
    @Autowired
    private PaymentVNpaySevice paymentVNpaySevice;

    @GetMapping("/cratePaymentVNpay")
    public ResponseEntity<PaymentResponseDTO> cratePaymentVNpay(@RequestParam long amount,
                                                                @RequestParam String bankCode,
                                                                @RequestParam String language,
                                                                @RequestParam String codeOrder,
                                                                HttpServletRequest request) throws UnsupportedEncodingException {

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(amount, bankCode, language, codeOrder);

        ResponseEntity<PaymentResponseDTO> responseEntity = paymentVNpaySevice.cratePaymentVNpay(paymentRequestDTO, request); // Truyền request vào service
        return responseEntity;
    }

    @GetMapping("/resultPayment")
    public ResponseEntity<Map<String, Object>> returnResultPayment(@RequestParam Map<String, String> allParams, HttpServletRequest request) throws UnsupportedEncodingException {
        boolean isValid = paymentVNpaySevice.validateSignature(allParams);
        Map<String, Object> response = new HashMap<>();
        if (isValid) {
            String responseCode = allParams.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                response.put("code", "00");
                response.put("message", "Thanh toán thành công");
                // Thêm thông tin đơn hàng
                response.put("codeOrder", allParams.get("vnp_TxnRef"));
                response.put("amount", allParams.get("vnp_Amount"));
                response.put("orderInfo", allParams.get("vnp_OrderInfo"));
                response.put("payDate", allParams.get("vnp_PayDate"));
                response.put("transactionNo", allParams.get("vnp_TransactionNo"));
            } else {
                response.put("code", responseCode);
                response.put("message", "Thanh toán thất bại: " + responseCode);
            }
        } else {
            response.put("code", "99");
            response.put("message", "Chữ ký không hợp lệ");
        }
        return ResponseEntity.ok(response);
    }
}
