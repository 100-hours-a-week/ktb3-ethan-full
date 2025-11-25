package archive.springtil.jpa.proxy;

import archive.springtil.domain.Member;
import archive.springtil.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class ProxyInitializationTest {
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
    @DisplayName("getReference 는 프록시를 반환하고 식별자 접근은 초기화를 유발하지 않는다")
    void getReference_returnProxy_and_IdAccessDoesNotInitialize() {
        Member proxy = em.getReference(Member.class, memberId);

        assertInstanceOf(HibernateProxy.class, proxy);

        assertFalse(Hibernate.isInitialized(proxy));

        Long id = proxy.getId();
        assertThat(id).isEqualTo(memberId);
        assertFalse(Hibernate.isInitialized(proxy));

        String name = proxy.getName();
        assertThat(name).isEqualTo("member1");
        assertTrue(Hibernate.isInitialized(proxy));

        assertInstanceOf(HibernateProxy.class, proxy);
    }

    @Test
    @DisplayName("find는 실제 엔티티를 반환한다.")
    void find_returnRealObject() {
        Member entity = em.find(Member.class, memberId);

        assertInstanceOf(Member.class, entity);

        assertTrue(Hibernate.isInitialized(entity));
    }

}
