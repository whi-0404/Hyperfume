package com.Hyperfume.Backend.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayCallBackDTO {
    String vnp_TmnCode;
    String vnp_Amount;
    String vnp_BankCode;
    String vnp_BankTranNo;
    String vnp_CardType;
    String vnp_PayDate;
    String vnp_OrderInfo;
    String vnp_TransactionNo;
    String vnp_ResponseCode;
    String vnp_TransactionStatus;
    String vnp_TxnRef;
    String vnp_SecureHashType;
    String vnp_SecureHash;
}
