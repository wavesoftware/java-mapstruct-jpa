package pl.wavesoftware.utils.mapstruct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.wavesoftware.utils.mapstruct.lang.TriConsumer;

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
