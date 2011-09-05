/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.console.test;

import lightframework.data.collections.test.*;
import lightframework.data.mysql.test.*;
import lightframework.data.criterion.operands.*;
import lightframework.data.criterion.Restrictions;

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
        
        Operand operand = Restrictions.clause(SqlClause.Where)
                .append(Restrictions.equal("Name", "TomDeng"))
                .append(Restrictions.And)
                .append(Restrictions.between("Age", 20, 30));
        
        System.out.print(operand);
    }
}
