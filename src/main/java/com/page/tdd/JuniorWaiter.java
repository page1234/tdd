package com.page.tdd;

import java.util.Arrays;
import java.util.List;

public class JuniorWaiter {

    List<StoreContentArk> storeContentArks;

    public JuniorWaiter(StoreContentArk firstStoreContentArk, StoreContentArk secondStoreContentArk) {
        storeContentArks = Arrays.asList(
                firstStoreContentArk,
                secondStoreContentArk
        );
    }

    public QRCode store(Bag bag) {
        return storeContentArks
                .get(0)
                .store(bag);
    }
}
