package com.maxzuo.jpa.repositorys;

import com.maxzuo.jpa.entity.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * 1.Repository是一个空接口，即是一个标记接口。
 * 2.若我们定义的接口继承了Repository，则该接口会被IOC容器识别为一个Repository Bean。
 *   纳入到IOC容器中，进而可以在该接口中定义满足一定规范的方法。
 *
 * 3.实际上，也可以通过@RepositoryDefinition注解来替代继承Repository接口。
 *
 * 4.在Repository接口中方法定义规范：
 *   1）按照SpringData的规范，查询方法以find|read|get开头，涉及条件查询时，
 *      条件的属性用条件关键字连接，要注意的是：条件属性以首字符大写。
 *   2）使用And条件连接时，应该写：
 *      findByLastNameAndFirstName(String lastName, String firstName);
 *      条件的属性名称 和 个数 要与 参数的位置与个数一一对应。
 *
 * 5.使用@Query注解可以自定义JPQL语句实现更灵活的查询；设置nativeQuery=true，使用原生查询。
 *
 * 6.为某个Repository自定义方法
 *
 * Created by zfh on 2019/04/21
 */
//@RepositoryDefinition(domainClass = Person.class, idClass = Integer.class)
//public interface PersonRepository extends Repository<Person, Integer>{
public interface PersonRepository extends CrudRepository<Person, Integer> {

    /**
     * 通过标准规范定义方法
     */
    Person getPersonByUsername(String username);

    /**
     * 传递参数，使用占位符
     */
    @Query("SELECT p FROM Person p WHERE p.id = ?1 AND p.username = ?2")
    Person getPersonByParams (Integer id, String username);

    /**
     * 通过参数名传递参数
     */
    @Query("SELECT p FROM Person p WHERE p.username = :username")
    Person getPersonByParams (@Param("username") String username);

    /**
     * 设置nativeQuery=true，使用原生查询
     */
    @Query(value = "SELECT id FROM person WHERE id = ?1", nativeQuery = true)
    Integer getPersonByNaviteQuery (Integer id);

    /**
     * 可以通过自定义的JPQL 完成 UPDATE 和 DELETE 操作，注意：JPQL不支持使用INSERT
     * 在 @Query注解中编写JPQL语句，但必须使用@Modifying进行修饰，已通知SpringData，
     * 这是一个UPDATE或DELETE操作。
     *
     * UPDATE或DELETE操作需要使用事务，此时需要定义Service层，在Service层的方法上添加事务操作
     * 默认情况下，SpringData每个方法上有事务，但都是一个只读事务，它们不能完成修改操作！
     */
    @Modifying
    @Query("UPDATE Person p SET p.username = :username WHERE p.id = :id")
    void updatePerson (@Param("id") Integer id, @Param("username") String username);
}
