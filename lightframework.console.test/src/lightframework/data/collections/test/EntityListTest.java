/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.collections.test;

import lightframework.data.collections.*;
import lightframework.data.entities.CategoryDTO;

/**
 *
 * @author Tom Deng
 */
public class EntityListTest {

    public void run() {
        EntityList<CategoryDTO> entities = new EntityList<CategoryDTO>(10);
        for (int i = 0; i < 10; i++) {
            CategoryDTO dto = new CategoryDTO("cate" + i, "cate" + i);
            dto.id = i;
            entities.add(dto);
        }

        entities.each(new Action<CategoryDTO>() {

            @Override
            public void execute(CategoryDTO element) {
                System.out.println(element.code);
            }
        });

        Enumerable<CategoryDTO> q = entities.where(new Predicate<CategoryDTO>() {

            @Override
            public boolean isMatch(CategoryDTO element) {
                return element.id % 2 == 0;
            }
        });

        q.each(new Action<CategoryDTO>() {

            @Override
            public void execute(CategoryDTO element) {
                System.out.print(element.code);
            }
        });

        EntityList<CategoryDTO> e = (EntityList<CategoryDTO>) q;
        e.each(new Action<CategoryDTO>() {

            @Override
            public void execute(CategoryDTO element) {
                System.out.println(element.code);
            }
        });
    }
}
