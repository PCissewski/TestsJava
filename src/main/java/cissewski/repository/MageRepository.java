package cissewski.repository;

import cissewski.entity.Mage;
import lombok.AllArgsConstructor;
import lombok.Builder;


import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Builder
public class MageRepository {
    private Collection<Mage> collection;

    public Optional<Mage> find(String name) {
        return collection.stream()
                .filter(mageOptional -> name.equals(mageOptional.getName()))
                .findFirst();
    }

    public void delete(String name) {

        Optional<Mage> mage = find(name);
        if (mage.isEmpty())
            throw new IllegalArgumentException();

        collection.remove(mage.get());
    }

    public void save(Mage mage) {
        if (find(mage.getName()).isPresent())
            throw new IllegalArgumentException();

        collection.add(mage);
    }
}
