package com.page.tdd;

import com.page.tdd.exception.StoreContentArkFullException;

import java.util.Arrays;
import java.util.List;

public class JuniorWaiter {

    List<StoreContentArk> storeContentArks;

    public JuniorWaiter(StoreContentArk... storeContentArk) {
        storeContentArks = Arrays.asList(storeContentArk);
    }

    public QRCode store(Bag bag) {
        StoreContentArk hadSpaceStoreContentArk = storeContentArks.stream()
                .filter(storeContentArk -> !storeContentArk.isFull())
                .findFirst()
                .orElseThrow(StoreContentArkFullException::new);

        return hadSpaceStoreContentArk.store(bag);
    }
}
