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
    Optional<User> findByVerificationToken(String token);

    @Modifying
    @Query(
            """
        UPDATE User SET firstName = :first,
            lastName = :last
        WHERE id = :userId
"""
    )
    void updateFirstAndLastNameByUserId(@Param("first") String first,@Param("last") String last, @Param("userId") Long userId);

    @Modifying
    @Query(
            """
        UPDATE User SET email = :email
        WHERE id = :userId
"""
    )
    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    @Modifying
    @Query(
            """
        UPDATE User SET phone = :phone
        WHERE id = :userId
"""
    )
    void updatePhoneNumber(@Param("phone") String phone, @Param("userId") Long userId);

    @Modifying
    @Query(
            """
        UPDATE User SET address = :address
        WHERE id = :userId
"""
    )
    void updateAddress(@Param("address") String address, @Param("userId") Long userId);

    @Modifying
    @Query(
            """
        UPDATE User SET password = :newPassword
        WHERE id = :userId
"""
    )
    void updatePassword(@Param("newPassword") String newPassword, @Param("userId") Long userId);

}
