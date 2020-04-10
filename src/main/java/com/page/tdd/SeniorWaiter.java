package com.page.tdd;

import java.util.*;

public class SeniorWaiter extends Waiter {

    public SeniorWaiter(List<StoreContentArk> storeContentArks) {
        this.storeContentArks = storeContentArks;
        this.cards = Collections.emptyList();
    }

    public SeniorWaiter() {
    }

    public SeniorWaiter(List<Card> cards, List<StoreContentArk> storeContentArks) {
        this.cards = new ArrayList<>(cards);
        this.storeContentArks = storeContentArks;
    }

    protected QRCode store(Bag bag) {
        verify();

        StoreContentArk storeContentArk = storeContentArks.stream()
                .max(Comparator.comparingInt(StoreContentArk::getFreeSpaceAmount))
                .get();

        return storeContentArk.store(bag);
    }
}
