package com.example.lab9.repositories;

import com.example.lab9.entities.Book;
import com.example.lab9.entities.ReadingList;

public class ReadingListRepositoryJPA extends AbstractRepositoryJPA<ReadingList, Integer> {
    @Override
    protected Class<ReadingList> getEntityClass() {
        return ReadingList.class;
    }

    @Override
    protected String getFindByNameQuery() {
        return "ReadingList.findByName";
    }

    @Override
    protected String getFindByIdQuery() {
        return "ReadingList.findById";
    }

}
