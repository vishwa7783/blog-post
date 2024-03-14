package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.TagRepository;
import com.io.mountblue.blogapplication.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    TagRepository tagRepository;
    TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }
    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }


}
