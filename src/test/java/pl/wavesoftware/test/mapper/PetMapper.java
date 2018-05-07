package pl.wavesoftware.test.mapper;

import org.hibernate.Hibernate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
  @Mapping(target = "reference", ignore = true)
  Pet map(PetJPA jpa, @Context CompositeContext context);
  @Mapping(target = "id", ignore = true)
  PetJPA map(Pet pet, @Context CompositeContext context);
  @InheritConfiguration
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

  default Set<PetJPA> petListToPetJPASet(List<Pet> list,
                                         @Context CompositeContext context) {
    return list.stream()
      .map(p -> map(p, context))
      .collect(Collectors.toSet());
  }

  @AfterMapping
  default void after(PetJPA petData, @MappingTarget Pet pet) {
    pet.setReference(petData.getId());
  }
}
