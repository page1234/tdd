package com.page.tdd;

import com.page.tdd.exception.StoreBagFailException;

public class StoreContentArk {

    private final int space;

    private int hadStoredCount;

    public StoreContentArk(int space) {
        this.space = space;
    }

    public QRCode store(Bag bag) {
        if (hadStoredCount >= space) {
            throw new StoreBagFailException();
        }

        hadStoredCount++;

        return new QRCode();
    }
}
