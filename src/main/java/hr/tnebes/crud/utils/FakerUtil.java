package hr.tnebes.crud.utils;

import com.github.javafaker.Faker;

public final class FakerUtil {

    public static final Faker faker = new Faker(LocaleUtil.getLocale());

    private FakerUtil() {
    }

}
