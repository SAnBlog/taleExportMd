package com.example.markdownexport.dao;

import com.example.markdownexport.bean.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: shouliang.wang
 * @create: 2020-05-16 11:10
 * @description:
 **/
public interface  ContentsDao extends JpaRepository<Contents,Long> {

}
