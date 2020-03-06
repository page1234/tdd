package com.page.tdd;

import com.page.tdd.exception.StoreBagFailException;

import java.util.HashMap;
import java.util.Map;

public class StoreContentArk {

    private final int space;

    private int hadStoredCount;

    private final Map<QRCode, Bag> arks;

    public StoreContentArk(int space) {
        this.space = space;
        arks = new HashMap<>();
    }

    public QRCode store(Bag bag) {
        if (hadStoredCount >= space) {
            throw new StoreBagFailException();
        }

        hadStoredCount++;

        QRCode qrCode = new QRCode();
        arks.put(qrCode, bag);

        return qrCode;
    }

    public Bag pickUp(QRCode qrCode) {
        return arks.get(qrCode);
    }
}
