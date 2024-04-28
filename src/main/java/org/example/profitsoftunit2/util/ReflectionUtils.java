package org.example.profitsoftunit2.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for work with Class types
 */
@Slf4j
@UtilityClass
public class ReflectionUtils {

	public static Field[] getObjectFields(Class<?> type) {
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}

		return fields;
	}

	public static List<String> getNames(Class<?> type) {
		Field[] fields = type.getDeclaredFields();
		return Arrays.stream(fields)
				.map(ReflectionUtils::getFieldName)
				.toList();
	}

	private String getFieldName(Field field) {
		JsonProperty annotation = field.getAnnotation(JsonProperty.class);
		if (annotation == null) {
			return field.getName();
		}

		return annotation.value();
	}

	public boolean isTypeCollection(Class<?> type) {
		return Collection.class.isAssignableFrom(type);
	}

	public Object getFieldValueByName(Object object, String fieldName) {
		try {
			Field nameField = object.getClass().getDeclaredField(fieldName);
			nameField.setAccessible(true);
			return nameField.get(fieldName);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			log.warn("Field with name {} not found", fieldName);
		}

		return null;
	}
}
