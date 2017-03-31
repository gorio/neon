package br.com.banconeon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import br.com.banconeon.model.TransferHistory;
import br.com.banconeon.utils.Util;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)

public class HistoryTest {
    private TransferHistory mTransfer;

    @Before
    public void setup() {
        mTransfer = new TransferHistory("1", "1", "Eduardo Gorio", "11111", "http://");
        mTransfer.setValor(1.0);
    }

    @Test
    public void testGetValor() {
        assertEquals(1.0, mTransfer.getValor());
    }
}
