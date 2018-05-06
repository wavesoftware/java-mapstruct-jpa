package pl.wavesoftware.test.mapper;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.test.entity.Owner;
import pl.wavesoftware.test.jpa.OwnerJPA;
import pl.wavesoftware.utils.mapstruct.jpa.AbstractCompositeContextMapping;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;
import pl.wavesoftware.utils.mapstruct.jpa.Mapping;
import pl.wavesoftware.utils.mapstruct.jpa.MappingProvider;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@RequiredArgsConstructor
final class OwnerMappingProvider implements MappingProvider<Owner, OwnerJPA, CompositeContext> {
  private final OwnerMapper ownerMapper;

  @Override
  public Mapping<Owner, OwnerJPA, CompositeContext> provide() {
    return AbstractCompositeContextMapping.mapperFor(
      Owner.class, OwnerJPA.class,
      ownerMapper::updateFromOwner
    );
  }
}
