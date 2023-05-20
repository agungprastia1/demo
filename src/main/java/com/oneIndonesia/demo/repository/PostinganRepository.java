package com.oneIndonesia.demo.repository;

import com.oneIndonesia.demo.dto.GetDetailPostinganInf;
import com.oneIndonesia.demo.model.Postingan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

@Repository
public interface PostinganRepository extends JpaRepository<Postingan,Long> {
    @Query(value = "SELECT\n" +
            "\t*\n" +
            "from\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\ta.id,\n" +
            "\t\ta.title,\n" +
            "\t\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tca.category\n" +
            "\t\tFROM\n" +
            "\t\t\tcategory ca\n" +
            "\t\tWHERE\n" +
            "\t\t\tca.id = a.category_id) as category,\n" +
            "\t\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tusr.user\n" +
            "\t\tfrom\n" +
            "\t\t\tuser usr\n" +
            "\t\twhere\n" +
            "\t\t\tusr.id = a.recruiter) as reqruiter,\n" +
            "\t\ta.description,\n" +
            "\t\ta.due_date as dueDate,\n" +
            "\t\ta.salary,\n" +
            "\t\ta.create_at as createAt,\n" +
            "\t\ta.update_at as updateAt\n" +
            "\tfrom\n" +
            "\t\tpostingan a)b\n" +
            "where\n" +
            "(b.id = ?1\n" +
            "\t\tor \"All\" = ?1)\n" +
            "\tand (b.title LIKE %?2%\n" +
            "\t\tOR \"All\" like %?2%)\n" +
            "\tand (b.category like %?3%\n" +
            "\t\tOR \"All\" like %?3%)\n" +
            "\tand (b.reqruiter like %?4%\n" +
            "\t\tOR \"All\" like %?4%)\n" +
            "\tand(b.description like %?5%\n" +
            "\t\tOR \"All\" like %?5%)\n" +
            "\tand (b.dueDate like %?6%\n" +
            "\t\tOR \"All\" like %?6%)\n" +
            "\tand (b.salary like %?7%\n" +
            "\t\tOR \"All\" like %?7%)\n" +
            "\tand (b.createAt like %?8%\n" +
            "\t\tOR \"All\" like %?8%)\n" +
            "\tand (b.updateAt like %?9%\n" +
            "\t\tOR \"All\" like %?9%)",
            countQuery = "SELECT\n" +
                    "\tcount(*)\n" +
                    "from\n" +
                    "\t(\n" +
                    "\tSELECT\n" +
                    "\t\ta.id,\n" +
                    "\t\ta.title,\n" +
                    "\t\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tca.category\n" +
                    "\t\tFROM\n" +
                    "\t\t\tcategory ca\n" +
                    "\t\tWHERE\n" +
                    "\t\t\tca.id = a.category_id) as category,\n" +
                    "\t\t(\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tusr.user\n" +
                    "\t\tfrom\n" +
                    "\t\t\tuser usr\n" +
                    "\t\twhere\n" +
                    "\t\t\tusr.id = a.recruiter) as reqruiter,\n" +
                    "\t\ta.description,\n" +
                    "\t\ta.due_date as dueDate,\n" +
                    "\t\ta.salary,\n" +
                    "\t\ta.create_at as createAt,\n" +
                    "\t\ta.update_at as updateAt\n" +
                    "\tfrom\n" +
                    "\t\tpostingan a)b\n" +
                    "where\n" +
                    "(b.id = ?1\n" +
                    "\t\tor \"All\" = ?1)\n" +
                    "\tand (b.title LIKE %?2%\n" +
                    "\t\tOR \"All\" like %?2%)\n" +
                    "\tand (b.category like %?3%\n" +
                    "\t\tOR \"All\" like %?3%)\n" +
                    "\tand (b.reqruiter like %?4%\n" +
                    "\t\tOR \"All\" like %?4%)\n" +
                    "\tand(b.description like %?5%\n" +
                    "\t\tOR \"All\" like %?5%)\n" +
                    "\tand (b.dueDate like %?6%\n" +
                    "\t\tOR \"All\" like %?6%)\n" +
                    "\tand (b.salary like %?7%\n" +
                    "\t\tOR \"All\" like %?7%)\n" +
                    "\tand (b.createAt like %?8%\n" +
                    "\t\tOR \"All\" like %?8%)\n" +
                    "\tand (b.updateAt like %?9%\n" +
                    "\t\tOR \"All\" like %?9%)",nativeQuery = true)
    Page<GetDetailPostinganInf> getPostinganList(Serializable serializable, String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7, Pageable pageable);

}
