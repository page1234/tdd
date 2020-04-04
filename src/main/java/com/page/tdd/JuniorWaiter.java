package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;
import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JuniorWaiter {

    List<StoreContentArk> storeContentArks;

    public JuniorWaiter(StoreContentArk... storeContentArk) {
        storeContentArks = Arrays.asList(storeContentArk);
    }

    public QRCode store(Bag bag) {
        if (storeContentArks.isEmpty()) {
            throw new StoreBagFailException();
        }

        StoreContentArk hadSpaceStoreContentArk = storeContentArks.stream()
                .filter(storeContentArk -> !storeContentArk.isFull())
                .findFirst()
                .orElseThrow(StoreContentArkFullException::new);

        return hadSpaceStoreContentArk.store(bag);
    }

    public Bag pickUp(QRCode qrcode) {
        return (Bag) storeContentArks.stream()
                .map(storeContentArk -> pickUpFromStoreContentArk(qrcode, storeContentArk))
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .orElseGet(null);
    }

    private Optional<?> pickUpFromStoreContentArk(QRCode qrcode, StoreContentArk storeContentArk) {
        try {
            return Optional.ofNullable(storeContentArk.pickUp(qrcode));
        } catch (InvalidQRCodeException e) {
            return Optional.empty();
        }
    }
}
