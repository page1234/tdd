package com.page.tdd;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreResult {

    private QRCode qrCode;

    private Card card;
}
