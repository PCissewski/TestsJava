import cissewski.entity.Mage;
import cissewski.repository.MageRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

public class MageRepoTest {

    private MageRepository mageRepo;

    @Before
    public void init() {
        mageRepo = new MageRepository(new ArrayList<>());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenSaveObjectWhichAlreadyExists(){
        thrown.expect(IllegalArgumentException.class);
        mageRepo.save(Mage.builder().name("test").level(12).build());
        mageRepo.save(Mage.builder().name("test").level(1).build());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenDeleteWhichNotExist(){
        thrown.expect(IllegalArgumentException.class);
        mageRepo.delete("notExistMage");
    }

    @Test
    public void shouldReturnEmptyOptionalWhenObjectNotExist() {
        assertThat(mageRepo.find("notExistMage")).isEmpty();
    }

    @Test
    public void shouldReturnOptionalWhenObjectExist(){
        mageRepo.save(Mage.builder().name("testMage").level(7).build());

        Optional<Mage> expectedMage = Optional.ofNullable(Mage.builder().name("testMage").level(4).build());

        assertThat(mageRepo.find("testMage")).isEqualTo(expectedMage);
    }
}
