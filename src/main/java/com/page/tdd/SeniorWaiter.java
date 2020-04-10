package com.page.tdd;

import java.util.Comparator;
import java.util.List;

public class SeniorWaiter extends Waiter {

    public SeniorWaiter(List<StoreContentArk> storeContentArks) {
        this.storeContentArks = storeContentArks;
    }

    public SeniorWaiter() {
    }

    public StoreResult storeAndGivingCard(Bag bag) throws Exception {
        QRCode qrCode = store(bag);

        clockIn();

        pickUpOneCard();

        return StoreResult.builder()
                .qrCode(qrCode)
                .build();
    }

    protected QRCode store(Bag bag) throws Exception {
        verify();

        StoreContentArk storeContentArk = storeContentArks.stream()
                .max(Comparator.comparingInt(StoreContentArk::getFreeSpaceAmount))
                .orElseThrow(Exception::new);

        return storeContentArk.store(bag);
    }

    protected void clockIn() {

    }

    protected void pickUpOneCard() {

    }
}
