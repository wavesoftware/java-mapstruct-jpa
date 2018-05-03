package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.wavesoftware.lang.TriConsumer;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
@Getter
@RequiredArgsConstructor
public abstract class Mapping<I, O, C> implements TriConsumer<I, O, C> {
  private final Class<I> sourceClass;
  private final Class<O> targetClass;
  private final Class<C> contextClass;
}
