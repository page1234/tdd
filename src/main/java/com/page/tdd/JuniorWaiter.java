package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;

import java.util.*;

public class JuniorWaiter extends Waiter {

    public JuniorWaiter(StoreContentArk... storeContentArk) {
        storeContentArks = Arrays.asList(storeContentArk);
        cards = Collections.emptyList();
    }

    public JuniorWaiter(List<Card> cards, StoreContentArk... storeContentArk) {
        this.storeContentArks = Arrays.asList(storeContentArk);
        this.cards = new ArrayList<>(cards);
    }

    protected QRCode store(Bag bag) {
        verify();

        StoreContentArk hadSpaceStoreContentArk = storeContentArks.stream()
                .filter(storeContentArk -> !storeContentArk.isFull())
                .findFirst()
                .get();

        return hadSpaceStoreContentArk.store(bag);
    }
}
