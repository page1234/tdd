package com.page.tdd;

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
        if (arks.size() >= space) {
            throw new StoreBagFailException();
        }

        QRCode qrCode = new QRCode();
        arks.put(qrCode, bag);
        qrCodes.add(qrCode);

        return qrCode;
    }

    public Bag pickUp(QRCode qrCode) {
        if (qrCodes.contains(qrCode) && !arks.containsKey(qrCode)) {
            throw new QRCodeHadUsedException();
        }

        return arks.remove(qrCode);
    }
}
