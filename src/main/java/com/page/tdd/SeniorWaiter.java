package com.page.tdd;

import java.util.List;

public class SeniorWaiter {
    private List<StoreContentArk> storeContentArks;

    public SeniorWaiter(List<StoreContentArk> storeContentArks) {
        this.storeContentArks = storeContentArks;
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
        StoreContentArk storeContentArk = storeContentArks.stream()
                .max((firstStoreContentArk, secondStoreContentArk) -> {
                    if (firstStoreContentArk.getFreeSpaceAmount() > secondStoreContentArk.getFreeSpaceAmount()) {
                        return 1;
                    }
                    return -1;
                })
                .orElseThrow(Exception::new);
        return storeContentArk.store(bag);
    }

    protected void clockIn() {

    }

    protected void pickUpOneCard() {

    }
}
