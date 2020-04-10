package com.page.tdd;

import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Waiter {

    protected List<StoreContentArk> storeContentArks;

    protected void verify() {
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
}
