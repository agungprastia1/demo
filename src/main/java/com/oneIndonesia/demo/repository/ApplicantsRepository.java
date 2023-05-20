package com.oneIndonesia.demo.repository;

import com.oneIndonesia.demo.dto.GetApplicantListInf;
import com.oneIndonesia.demo.model.Applicants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface ApplicantsRepository extends JpaRepository<Applicants,Long> {
    @Query(value = "select * from(\n" +
            "\t\tselect\n" +
            "\tap.id,\n" +
            "\tca.category,\n" +
            "\tpo.title,\n" +
            "\tusr.user,\n" +
            "\tap.file_name as cvName,\n" +
            "\tap.path as cvPath,\n" +
            "\tap.status,\n" +
            "\tap.remark,\n" +
            "ap.create_at as createAt\n" +
            "from\n" +
            "\tapplicant as ap\n" +
            "left join postingan as po on\n" +
            "\tap.postingan_id = po.id\n" +
            "left join category as ca ON\n" +
            "\tpo.category_id = ca.id\n" +
            "left join user usr ON\n" +
            "\tap.jobseeker = usr.id\n" +
            "WHERE\n" +
            "(ap.id = ?1 or \"All\" = ?1)\n" +
            "and(po.title like %?2% or \"All\" like %?2%)\n" +
            "and(ap.file_name like %?3% or \"All\" like %?3%)\n" +
            "and(ap.status like %?4% or \"All\" like %?4% )\n" +
            "and(ap.remark like %?5% or \"All\" like %?5%) \n" +
            "and(ap.create_at like %?8% or \"All\" like %?8%))a \n" +
            "where\n" +
            "(a.category like %?6% or \"All\" like %?6%)\n" +
            "and(a.user like %?7% or \"All\" like %?7%)",
            countQuery = "select * from(\n" +
                    "\t\tselect\n" +
                    "\tap.id,\n" +
                    "\tca.category,\n" +
                    "\tpo.title,\n" +
                    "\tusr.user,\n" +
                    "\tap.file_name as cvName,\n" +
                    "\tap.path as cvPath,\n" +
                    "\tap.status,\n" +
                    "\tap.remark,\n" +
                    "ap.create_at as createAt\n" +
                    "from\n" +
                    "\tapplicant as ap\n" +
                    "left join postingan as po on\n" +
                    "\tap.postingan_id = po.id\n" +
                    "left join category as ca ON\n" +
                    "\tpo.category_id = ca.id\n" +
                    "left join user usr ON\n" +
                    "\tap.jobseeker = usr.id\n" +
                    "WHERE\n" +
                    "(ap.id = ?1 or \"All\" = ?1)\n" +
                    "and(po.title like %?2% or \"All\" like %?2%)\n" +
                    "and(ap.file_name like %?3% or \"All\" like %?3%)\n" +
                    "and(ap.status like %?4% or \"All\" like %?4% )\n" +
                    "and(ap.remark like %?5% or \"All\" like %?5%) \n" +
                    "and(ap.create_at like %?8% or \"All\" like %?8%))a \n" +
                    "where\n" +
                    "(a.category like %?6% or \"All\" like %?6%)\n" +
                    "and(a.user like %?7% or \"All\" like %?7%)",nativeQuery = true)
    Page<GetApplicantListInf> getListApplicant(Serializable serializable, String s, String s1, String s2, String s3, String s4, String s5, String s6, Pageable pageable);

}
