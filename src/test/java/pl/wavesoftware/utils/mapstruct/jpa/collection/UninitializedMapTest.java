package pl.wavesoftware.utils.mapstruct.jpa.collection;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-06
 */
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class UninitializedMapTest {
  private final Method method;
  private final Map<Object, Object> list = new UninitializedMap<>(
    TestThing.class, OtherTestThing.class
  );

  @Parameters(name = "{0}")
  public static Iterable<Method> methods() {
    Method[] methods = UninitializedMap.class.getDeclaredMethods();
    return Arrays.stream(methods)
      .filter(m -> Modifier.isPublic(m.getModifiers()))
      .filter(m -> !"toString".equals(m.getName()))
      .collect(Collectors.toList());
  }

  @Test
  public void testToString() {
    assertThat(list.toString()).isEqualTo("UninitializedMap<TestThing,OtherTestThing>");
  }

  @Test
  public void testMethodThrowViaReflection() throws
    IllegalAccessException, InstantiationException {
    // given
    Object[] args = prepareArgs();

    // when
    try {
      method.invoke(list, args);
      failBecauseExceptionWasNotThrown(InvocationTargetException.class);
    } catch (InvocationTargetException ex) {
      // then
      assertThat(ex).hasCauseInstanceOf(LazyInitializationException.class);
      assertThat(ex.getCause())
        .hasMessage(
          "Trying to use uninitialized collection for type: " +
            "Map<TestThing,OtherTestThing>. You need to fetch this collection before using it, " +
            "for ex. using JOIN FETCH in JPQL. This exception prevents lazy loading n+1 problem."
        );
    }
  }

  private Object[] prepareArgs() throws IllegalAccessException, InstantiationException {
    Object[] objects = new Object[method.getParameterCount()];
    for (int i = 0; i < method.getParameterCount(); i++) {
      Parameter parameter = method.getParameters()[i];
      Object obj = newInstanceOfParameter(parameter);
      objects[i] = obj;
    }
    return objects;
  }

  private static Object newInstanceOfParameter(Parameter parameter) throws
    InstantiationException, IllegalAccessException {
    Class<?> type = parameter.getType();
    if (type.isPrimitive()) {
      if (type == int.class) {
        return 1;
      } else if (type == boolean.class) {
        return true;
      }
    }
    if (type.isArray()) {
      return new Object[1];
    }
    if (type == Map.class) {
      return new HashMap<>();
    }
    if (type == Collection.class) {
      return new ArrayList<>();
    }
    return parameter.getType().newInstance();
  }

  private interface OtherTestThing {

  }

  private interface TestThing {

  }
}
