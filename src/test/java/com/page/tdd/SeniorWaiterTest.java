package com.page.tdd;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

public class SeniorWaiterTest {

    @Test
    void should_invoked_store_and_clock_in_and_pick_up_one_card_then_store_and_giving_card_given_a_bag_and_a_senior_waiter() throws Exception {
        Bag bag = new Bag();
        SeniorWaiter seniorWaiter = Mockito.mock(SeniorWaiter.class);
        Mockito.doCallRealMethod()
                .when(seniorWaiter)
                .storeAndGivingCard(bag);

        seniorWaiter.storeAndGivingCard(bag);

        verify(seniorWaiter).store(bag);
        verify(seniorWaiter).clockIn();
        verify(seniorWaiter).pickUpOneCard();
    }

    @Test
    void should_got_a_qr_code_when_store_and_giving_card_given_a_bag_and_a_senior_waiter_and_a_store_content_ark_with_1_space_and_a_store_content_ark_with_2_space() throws Exception {
        Bag bag = new Bag();
        StoreContentArk storeContentArk = new StoreContentArk(2);
        List<StoreContentArk> storeContentArks = Arrays.asList(new StoreContentArk(1), storeContentArk);
        SeniorWaiter seniorWaiter = new SeniorWaiter(storeContentArks);

        StoreResult storeResult = seniorWaiter.storeAndGivingCard(bag);

        then(storeResult).isNotNull();
        QRCode qrCode = storeResult.getQrCode();
        then(qrCode).isNotNull();
        then(storeContentArk.pickUp(qrCode)).isEqualTo(bag);
    }
}
