package org.example.Lab11.repositories;

import org.example.Lab11.entities.ReadingList;
import org.springframework.stereotype.Repository;

@Repository
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
