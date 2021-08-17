package com.codecool.gladiator.dao;

import com.codecool.gladiator.dao.implementation.database.GladiatorHistoryDaoDatabase;
import com.codecool.gladiator.dao.implementation.mem.GladiatorHistoryDaoMem;

public class DaoImplementation {
    private final GladiatorHistoryDao gladiatorHistoryDao;

    public DaoImplementation(DaoType daoType) {
        switch (daoType) {
            case DATABASE:
                gladiatorHistoryDao = new GladiatorHistoryDaoDatabase();
                break;
            case MEM:
            default:
                gladiatorHistoryDao = GladiatorHistoryDaoMem.getInstance();
        }
    }

    public GladiatorHistoryDao getGladiatorHistoryDao() {
        return gladiatorHistoryDao;
    }
}
