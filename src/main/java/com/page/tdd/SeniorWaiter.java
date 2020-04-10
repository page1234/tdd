package com.page.tdd;

import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SeniorWaiter {
    private List<StoreContentArk> storeContentArks;

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

    private void verify() {
        verifyHadStoreContentArksCouldUse();

        verifyNotAllStoreContentArksAreFull();
    }

    private void verifyNotAllStoreContentArksAreFull() {
        List<StoreContentArk> notFullStoreContentArks = storeContentArks.stream()
                .filter(storeContentArk -> !storeContentArk.isFull())
                .collect(Collectors.toList());
        if (notFullStoreContentArks.isEmpty()) {
            throw new StoreContentArkFullException();
        }
    }

    private void verifyHadStoreContentArksCouldUse() {
        if (Objects.isNull(storeContentArks)) {
            throw new StoreBagFailException();
        }
    }

    protected void clockIn() {

    }

    protected void pickUpOneCard() {

    }
}
