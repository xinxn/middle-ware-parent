package com.csv.manage.service;

import java.util.List;

public interface CsvService {

    void readNotify (String path, Integer columnSize) throws Exception ;

    void createCsv (List<String> dataList,String path) throws Exception ;
}
