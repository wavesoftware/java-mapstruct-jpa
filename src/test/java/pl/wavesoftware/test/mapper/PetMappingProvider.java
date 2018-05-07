package pl.wavesoftware.test.mapper;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.jpa.PetJPA;
import pl.wavesoftware.utils.mapstruct.jpa.AbstractCompositeContextMapping;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;
import pl.wavesoftware.utils.mapstruct.jpa.Mapping;
import pl.wavesoftware.utils.mapstruct.jpa.MappingProvider;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@RequiredArgsConstructor
final class PetMappingProvider implements MappingProvider<Pet, PetJPA, CompositeContext> {

  private final PetMapper petMapper;

  @Override
  public Mapping<Pet, PetJPA, CompositeContext> provide() {
    return AbstractCompositeContextMapping.mappingFor(
      Pet.class, PetJPA.class,
      petMapper::updateFromPet
    );
  }
}
