package com.telekom.dao.api;

import com.telekom.model.entity.Option;

import java.util.List;

public interface OptionDao extends PaginationDao {

    List<Option> findByTariffParents(Integer tariffId);

    void add(Option option);

    Option getOne(Integer id);

    List getAllValid();
    List<Option> getAllNoParentNoGroup(Integer size, Integer page);
    Long getPagesCountNoParentNoGroup();
    List<Option> getAllNoParentNoGroupExisting(Integer size, Integer page, Integer id);
    Long getPagesCountNoParentNoGroupExisting(Integer id);
    List<Option> findByTariffChildren(Integer id);
    List<Option> getAllNoParent(Integer size, Integer page);
    Long getPagesCountNoParent();
    List<Option> getAllNoChildrenAndParent(Integer size, Integer page);
    Long getPagesCountNoParentNoChildren();
    List<Option> getAllNoChildrenAndParentExisting(Integer size, Integer page, Integer id);
    Long getPagesNoChildrenAndParentExisting(Integer id);
}

