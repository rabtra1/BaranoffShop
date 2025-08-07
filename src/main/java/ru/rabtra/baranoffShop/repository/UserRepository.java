package ru.rabtra.baranoffShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rabtra.baranoffShop.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query(
            """
        UPDATE User SET firstName = :first,
            lastName = :last
        WHERE id = :userId
"""
    )
    void updateFirstAndLastNameByUserId(@Param("first") String first,@Param("last") String last, @Param("userId") Long userId);

}
