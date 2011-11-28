/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lightframework.data.collections.test;

import lightframework.data.collections.*;
import lightframework.data.entities.CategoryEntity;

/**
 *
 * @author Tom Deng
 */
public class EntityListTest {

    public void run() {
        MyArrayList<CategoryEntity> entities = new MyArrayList<CategoryEntity>(10);
        for (int i = 0; i < 10; i++) {
            CategoryEntity dto = new CategoryEntity(i, "cate" + i, "cate" + i);
            dto.setId(i);
            entities.add(dto);
        }

        entities.each(new Action<CategoryEntity>() {

            @Override
            public void execute(CategoryEntity element) {
                System.out.println(element.getDescription());
            }
        });

        Enumerable<CategoryEntity> q = entities.where(new Predicate<CategoryEntity>() {

            @Override
            public boolean isMatch(CategoryEntity element) {
                return element.getId() % 2 == 0;
            }
        });

        q.each(new Action<CategoryEntity>() {

            @Override
            public void execute(CategoryEntity element) {
                System.out.print(element.getDescription());
            }
        });

        MyArrayList<CategoryEntity> e = (MyArrayList<CategoryEntity>) q;
        e.each(new Action<CategoryEntity>() {

            @Override
            public void execute(CategoryEntity element) {
                System.out.println(element.getDescription());
            }
        });
    }
}
