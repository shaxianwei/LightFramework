/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.entity;

import lightframework.data.annotations.Column;

/**
 *
 * @author Tom Deng
 */
public class CategoryEntity {

    public final static String ENTITYNAME = "Category";
    public final static String C_Id = "Id";
    public final static String C_Name = "Name";
    public final static String C_Description = "Description";
    @Column(name = "Name")
    private  String name;
    @Column(name = "Id", isPrimaryKey = true)
    private Integer id;
    @Column(name = "Description")
    private String description;

    public CategoryEntity() {
    }

    public CategoryEntity(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }
}
