/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.mysql.test;

import lightframework.data.entities.CategoryDTO;
import java.util.*;

/**
 *
 * @author Tom Deng
 */
public class CategoryTest {

    private final static String url = "jdbc:mysql://192.168.1.251/test?user=root&password=ddd";

    public void run() {
        Category category = new Category(url);
        List<CategoryDTO> categories = category.select();
        for (int i = 0; i < 10; i++) {
            CategoryDTO dto = new CategoryDTO(i,"test" + i, "test1" + i);
            int id = category.insertWithId(dto);
        }
        
        List<CategoryDTO> oneCategory = category.getCategoryById(8);
        if(oneCategory == null || oneCategory.isEmpty()) return;
        System.out.println(oneCategory.get(0).getId());
    }
}
