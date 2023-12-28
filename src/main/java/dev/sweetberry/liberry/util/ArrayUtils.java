package dev.sweetberry.liberry.util;

import java.util.function.Consumer;

public class ArrayUtils {
	public static <T> void forEach(T[] arr, Consumer<T> function) {
		for (var t : arr)
			function.accept(t);
	}
}
