package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;
import com.page.tdd.exception.QRCodeHadUsedException;
import com.page.tdd.exception.StoreBagFailException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreContentArk {

    private final int space;

    private final Map<QRCode, Bag> arks;

    private final List<QRCode> qrCodes;

    public StoreContentArk(int space) {
        this.space = space;
        arks = new HashMap<>();
        qrCodes = new ArrayList<>();
    }

    public QRCode store(Bag bag) {
        if (isFull()) {
            throw new StoreBagFailException();
        }

        QRCode qrCode = new QRCode();
        arks.put(qrCode, bag);
        qrCodes.add(qrCode);

        return qrCode;
    }

    public Bag pickUp(QRCode qrCode) {
        verifyQRCode(qrCode);

        return arks.remove(qrCode);
    }

    private void verifyQRCode(QRCode qrCode) {
        if (isInvalidQRCode(qrCode)) {
            throw new InvalidQRCodeException();
        }

        if (isQRCodeUsed(qrCode)) {
            throw new QRCodeHadUsedException();
        }
    }

    private boolean isInvalidQRCode(QRCode qrCode) {
        return !qrCodes.contains(qrCode) && !arks.containsKey(qrCode);
    }

    private boolean isQRCodeUsed(QRCode qrCode) {
        return qrCodes.contains(qrCode) && !arks.containsKey(qrCode);
    }

    public boolean isFull() {
        return arks.size() == space;
    }
}
