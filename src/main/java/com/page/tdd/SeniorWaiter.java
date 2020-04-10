package com.page.tdd;

import java.util.List;

public class SeniorWaiter {
    public SeniorWaiter(List<StoreContentArk> storeContentArks) {

    }

    public StoreResult storeAndGivingCard(Bag bag) {
        store();

        clockIn();

        pickUpOneCard();

        return null;
    }

    protected void store() {

    }

    protected void clockIn() {

    }

    protected void pickUpOneCard() {

    }
}
