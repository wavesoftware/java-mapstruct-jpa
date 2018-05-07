package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An abstract mapping that holds all classes representations.
 *
 * @param <I> a type of input object for map from
 * @param <O> a type of output (target) object for map to
 * @param <C> a type of context to be used in the mapping
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 25.04.18
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractMapping<I, O, C> implements Mapping<I, O, C> {
  private final Class<I> sourceClass;
  private final Class<O> targetClass;
  private final Class<C> contextClass;
}
