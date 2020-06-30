/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module GradleMorphiaTest {
    requires core;
    requires mongo.java.driver;
    requires proxytoys;
    requires io.github.classgraph;
    requires slf4j.api;
    
    requires java.sql;
    
    exports GradleMorphiaTest;
}
