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

    public StoreResult storeAndGivingCard(Bag bag) throws Exception {
        QRCode qrCode = store(bag);

        clockIn();

        Optional<Card> cardOptional = pickUpOneCard();

        return StoreResult.builder()
                .qrCode(qrCode)
                .card(cardOptional.orElse(null))
                .build();
    }

    protected QRCode store(Bag bag) throws Exception {
        verify();

        StoreContentArk storeContentArk = storeContentArks.stream()
                .max(Comparator.comparingInt(StoreContentArk::getFreeSpaceAmount))
                .orElseThrow(Exception::new);

        return storeContentArk.store(bag);
    }
}
