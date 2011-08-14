/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.entities;

import lightframework.data.annotations.Column;

/**
 *
 * @author Tom Deng
 */
public class CategoryDTO {

    public final static String ENTITYNAME = "Category";
    public final static String C_CategoryId = "CategoryId";
    public final static String C_Name = "Name";
    public final static String C_Code = "Code";
    @Column(name = "Name")
    public String name;
    @Column(name = "CategoryId", isIdentity = true, isPrimaryKey = true)
    public Integer id;
    @Column(name = "Code")
    public String code;

    public CategoryDTO(){
        
    }
    
    public CategoryDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }
}
