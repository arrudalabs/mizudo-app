package io.github.arrudalabs.mizudo;


import com.github.javafaker.Faker;

import javax.transaction.Transactional;
import java.util.function.Supplier;

public class BaseTest {

    public final Faker faker = new Faker();

    @Transactional
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Transactional
    public <T> T executeAndReturn(Supplier<T> supplier) {
        return supplier.get();
    }


}
