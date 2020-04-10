package com.page.tdd;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class SeniorWaiterTest {

    @Test
    void should_invoked_store_and_clock_in_and_pick_up_one_card_then_store_and_giving_card_given_a_bag_and_a_senior_waiter() {
        Bag bag = new Bag();
        SeniorWaiter seniorWaiter = Mockito.mock(SeniorWaiter.class);
        Mockito.doCallRealMethod()
                .when(seniorWaiter)
                .storeAndGivingCard(bag);

        seniorWaiter.storeAndGivingCard(bag);

        verify(seniorWaiter).store();
        verify(seniorWaiter).clockIn();
        verify(seniorWaiter).pickUpOneCard();
    }
}
