package pl.wavesoftware.test.mapper;

import org.hibernate.Hibernate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.jpa.PetJPA;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;
import pl.wavesoftware.utils.mapstruct.jpa.collection.UninitializedList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Mapper(
  uses = { OwnerMapper.class, ToyMapper.class },
  componentModel = "jsr330"
)
interface PetMapper {
  Pet map(PetJPA jpa, @Context CompositeContext context);
  PetJPA map(Pet pet, @Context CompositeContext context);
  void updateFromPet(Pet pet,
                     @MappingTarget PetJPA jpa,
                     @Context CompositeContext context);

  default List<Pet> petJPASetToPetList(Set<PetJPA> set, @Context CompositeContext context) {
    if (!Hibernate.isInitialized(set)) {
      return new UninitializedList<>(PetJPA.class);
    }
    return set.stream()
      .map(j -> map(j, context))
      .collect(Collectors.toList());
  }

  @AfterMapping
  default void after(PetJPA petData, @MappingTarget Pet pet) {
    pet.setReference(petData.getId());
  }
}
