package com.oneIndonesia.demo.repository;

import com.oneIndonesia.demo.dto.GetUserLilstInf;
import com.oneIndonesia.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<Object> findByEmail(String username);

    @Query(value = "SELECT * FROM `user` a where a.user like %?1% or \"All\" like %?1% ;",nativeQuery = true)
    Optional<User> getByName(String recruiter);

    @Query(value = "SELECT\n" +
            "*\n" +
            "FROM(\n" +
            "SELECT\n" +
            "\tusr.id,\n" +
            "\tusr.user,\n" +
            "\tusr.email,\n" +
            "\tusr.no_hp as noHp,\n" +
            "\trl.role,\n" +
            "\tusr.create_at as createAt,\n" +
            "\tusr.updated_at as updeteAt\n" +
            "FROM\n" +
            "\t`user`\n" +
            " as usr\n" +
            "left join role as rl ON\n" +
            "\tusr.role_id = rl.id\n" +
            "    where\n" +
            "(usr.id = ?1 or \"All\"=?1)\n" +
            "and(usr.user like %?2% or \"All\" like %?2%)\n" +
            "and(usr.email like %?3% or \"All\" like %?3%)\n" +
            "and(usr.no_hp like %?4% or \"All\" like %?4%)\n" +
            "and(usr.create_at like %?5% or \"All\" like %?5%)\n" +
            "and(usr.updated_at like %?6% or \"All\" like %?6%))a\n" +
            "where \n" +
            "(a.role like %?7% or \"All\" like %?7%)",
    countQuery = "SELECT\n" +
            "count(*)\n" +
            "FROM(\n" +
            "SELECT\n" +
            "\tusr.id,\n" +
            "\tusr.user,\n" +
            "\tusr.email,\n" +
            "\tusr.no_hp as noHp,\n" +
            "\trl.role,\n" +
            "\tusr.create_at as createAt,\n" +
            "\tusr.updated_at as updeteAt\n" +
            "FROM\n" +
            "\t`user`\n" +
            " as usr\n" +
            "left join role as rl ON\n" +
            "\tusr.role_id = rl.id\n" +
            "    where\n" +
            "(usr.id = ?1 or \"All\"=?1)\n" +
            "and(usr.user like %?2% or \"All\" like %?2%)\n" +
            "and(usr.email like %?3% or \"All\" like %?3%)\n" +
            "and(usr.no_hp like %?4% or \"All\" like %?4%)\n" +
            "and(usr.create_at like %?5% or \"All\" like %?5%)\n" +
            "and(usr.updated_at like %?6% or \"All\" like %?6%))a\n" +
            "where \n" +
            "(a.role like %?7% or \"All\" like %?7%)",nativeQuery = true)
    Page<GetUserLilstInf> getListUser(Serializable serializable, String s, String s1, String s2, String s3, String s4, String s5, Pageable pageable);
}
