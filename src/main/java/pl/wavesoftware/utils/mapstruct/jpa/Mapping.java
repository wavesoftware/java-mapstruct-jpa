package pl.wavesoftware.utils.mapstruct.jpa;

import pl.wavesoftware.lang.TriConsumer;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
public interface Mapping<I, O, C> extends TriConsumer<I, O, C> {
  Class<I> getSourceClass();

  Class<O> getTargetClass();

  Class<C> getContextClass();
}
