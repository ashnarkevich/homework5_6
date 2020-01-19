package com.gmail.petrikov05;

import java.sql.SQLException;

import com.gmail.petrikov05.controler.HomeWorkService;
import com.gmail.petrikov05.controler.impl.HomeWorkServiceImpl;

public class App {

    public static void main(String[] args) throws SQLException {
        HomeWorkService homeWorkService = HomeWorkServiceImpl.getInstance();
        homeWorkService.runTaskA();
        homeWorkService.runTaskB();
        homeWorkService.runTaskC();
        homeWorkService.runTaskD();
        homeWorkService.runTaskE();
        homeWorkService.runTaskF();
        homeWorkService.runTaskG();
        homeWorkService.runTaskH();
    }

}
