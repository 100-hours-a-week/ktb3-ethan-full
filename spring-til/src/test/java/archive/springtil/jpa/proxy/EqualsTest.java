package archive.springtil.jpa.proxy;

import archive.springtil.domain.Member;
import archive.springtil.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EqualsTest {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    private Long memberId;

    @BeforeEach
    void setUp() {
        Member saved = memberRepository.save(
                Member.builder()
                        .name("member1")
                        .build()
        );
        memberId = saved.getId();

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("JPA 엔티티의 비교는 동일성 비교를 기본으로 한다.")
    void entity_equals_use_same_identity() {
        Member a = em.find(Member.class, memberId);
        Member b = em.find(Member.class, memberId);

        assertThat(a == b).isTrue();
        assertThat(a.equals(b)).isTrue();
    }
}
