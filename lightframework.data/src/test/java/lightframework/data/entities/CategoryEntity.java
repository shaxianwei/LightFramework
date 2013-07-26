package lightframework.data.entities;

import lightframework.data.annotations.Column;

/**
 *
 * @author Tom Deng
 */
public class CategoryEntity {

    public final static String EntityName = "Category";
    public final static String Id = "Id";
    public final static String Name = "Name";
    public final static String Description = "Description";
    @Column(name = "Name")
    private String name;
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
