package com.page.tdd;

import com.page.tdd.exception.InvalidQRCodeException;
import com.page.tdd.exception.StoreBagFailException;
import com.page.tdd.exception.StoreContentArkFullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JuniorWaiter {

    private List<StoreContentArk> storeContentArks;

    private ArrayList<Record> records = new ArrayList<>();

    private List<Card> cards;

    public JuniorWaiter(StoreContentArk... storeContentArk) {
        storeContentArks = Arrays.asList(storeContentArk);
    }

    public JuniorWaiter(List<Card> cards, StoreContentArk... storeContentArk) {
        this.storeContentArks = Arrays.asList(storeContentArk);
        this.cards = new ArrayList<>(cards);
    }

    protected QRCode store(Bag bag) {
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
                .orElseThrow(InvalidQRCodeException::new);
    }

    private Optional<?> pickUpFromStoreContentArk(QRCode qrcode, StoreContentArk storeContentArk) {
        try {
            return Optional.ofNullable(storeContentArk.pickUp(qrcode));
        } catch (InvalidQRCodeException e) {
            return Optional.empty();
        }
    }

    public StoreResult storeAndGivingCard(Bag bag) {
        QRCode qrCode = store(bag);

        clockIn();

        Optional<Card> cardOptional = pickUpOneCard();

        return StoreResult.builder()
                .qrCode(qrCode)
                .card(cardOptional.orElse(null))
                .build();
    }

    public void clockIn() {
        records.add(new Record());
    }

    public Optional<Card> pickUpOneCard() {
        return cards.size() > 0 ?
                Optional.ofNullable(cards.remove(cards.size() - 1)) :
                Optional.empty();
    }

    public List<Record> getRecords() {
        return records;
    }
}
