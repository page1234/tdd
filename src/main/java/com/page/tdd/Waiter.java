package com.page.tdd;

import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Waiter {

    protected List<StoreContentArk> storeContentArks;

    private ArrayList<Record> records = new ArrayList<>();

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
        if (Objects.isNull(storeContentArks) || storeContentArks.isEmpty()) {
            throw new StoreBagFailException();
        }
    }

    public void clockIn() {
        records.add(new Record());
    }

    public List<Record> getRecords() {
        return records;
    }
}
