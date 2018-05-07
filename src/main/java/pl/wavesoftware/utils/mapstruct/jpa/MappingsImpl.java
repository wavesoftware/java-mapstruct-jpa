package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 07.05.18
 */
@RequiredArgsConstructor
final class MappingsImpl<C> implements Mappings<C> {
  private final Class<C> contextClass;
  private final Iterable<Mapping<?, ?, ?>> mappings;

  @Override
  @SuppressWarnings("unchecked")
  public <I, O> Mapping<I, O, C> getMapping(Class<I> sourceClass,
                                            Class<O> targetClass) {
    for (Mapping<?, ?, ?> mapping : mappings) {
      if (isSuitedFor(sourceClass, targetClass, mapping)) {
        return (Mapping<I, O, C>) mapping;
      }
    }
    throw new EidIllegalStateException(
      new Eid("20180425:135245"),
      "Mapping for source class %s and target class %s is not configured! You should probably " +
        "implement and configure MappingProvider<%s,%s,%s>.",
      sourceClass.getName(), targetClass.getName(), sourceClass.getName(),
      targetClass.getName(), contextClass.getName()
    );
  }

  private <I, O> boolean isSuitedFor(Class<I> sourceClass,
                                     Class<O> targetClass,
                                     Mapping<?, ?, ?> mapping) {
    return contextClass.isAssignableFrom(mapping.getContextClass())
      && mapping.getSourceClass() == sourceClass
      && mapping.getTargetClass() == targetClass;
  }
}
