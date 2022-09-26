package hr.tnebes.crud.utils;

import com.github.javafaker.Faker;

public class FakerUtil {

    public static final Faker faker = new Faker(Util.getLocale());

    private FakerUtil() {
    }


}
