/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.criterion.operands;

/**
 *
 * @author Tom Deng <xianrendzw@hotmail.com>
 */
public enum Bracket  {
        Left("("), Rgiht(")");
        
        private Bracket(String abbreviation){
            this.abbreviation = abbreviation;
        }
        
        public String getAbbreviation(){
            return this.abbreviation;
        }
        
        private String abbreviation;
}
