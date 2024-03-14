package com.io.mountblue.blogapplication.dao;

import com.io.mountblue.blogapplication.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    @Query("select t from Tag t where t.name = :name")
    Tag findTagByName(String name);

}
