package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;
import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Waiter {

    protected List<StoreContentArk> storeContentArks;

    private ArrayList<Record> records = new ArrayList<>();

    protected List<Card> cards;

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

    public Optional<Card> pickUpOneCard() {
        return cards.size() > 0 ?
                Optional.ofNullable(cards.remove(cards.size() - 1)) :
                Optional.empty();
    }

    protected abstract QRCode store(Bag bag);

    public StoreResult storeAndGivingCard(Bag bag) {
        QRCode qrCode = store(bag);

        clockIn();

        Optional<Card> cardOptional = pickUpOneCard();

        return StoreResult.builder()
                .qrCode(qrCode)
                .card(cardOptional.orElse(null))
                .build();
    }

    public Bag pickUp(QRCode qrcode) {
        return (Bag) storeContentArks.stream()
                .map(storeContentArk -> pickUpFromStoreContentArk(qrcode, storeContentArk))
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get)
                .orElseThrow(InvalidQRCodeException::new);
    }

    private Optional<?> pickUpFromStoreContentArk(QRCode qrcode, StoreContentArk storeContentArk) {
        try {
            return Optional.ofNullable(storeContentArk.pickUp(qrcode));
        } catch (InvalidQRCodeException e) {
            return Optional.empty();
        }
    }
}
