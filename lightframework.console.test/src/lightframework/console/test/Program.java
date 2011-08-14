/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.console.test;

import lightframework.data.collections.test.EntityListTest;
import lightframework.data.mysql.test.CategoryTest;

/**
 *
 * @author tomdeng
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CategoryTest categoryTest = new CategoryTest();
        categoryTest.run();
        
        EntityListTest entityListTest = new EntityListTest();
        entityListTest.run();
    }
}
