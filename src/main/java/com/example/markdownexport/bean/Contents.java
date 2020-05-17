package com.example.markdownexport.bean;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: shouliang.wang
 * @create: 2020-05-16 11:07
 * @description:
 **/
@Entity()
@Table(name = "contents")
@Data
public class Contents {

    @Id
    private Integer cid;

    private String title;

    private String content;
}
