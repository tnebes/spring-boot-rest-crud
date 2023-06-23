package hr.tnebes.crud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {

    private ResourceUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T modelFromResource(final Resource resource, final Class<T> clazz) throws IOException {
        try (final InputStream inputStream = resource.getInputStream()) {
            return new ObjectMapper().readValue(inputStream, clazz);
        }
    }

    public static String jsonFromResource(final Resource resource, final Class<?> clazz) throws IOException {
        return new ObjectMapper().writeValueAsString(ResourceUtil.modelFromResource(resource, clazz));
    }

}
