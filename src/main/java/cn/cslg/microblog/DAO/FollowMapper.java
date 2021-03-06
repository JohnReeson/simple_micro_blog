package cn.cslg.microblog.DAO;

import java.util.List;

import cn.cslg.microblog.PO.Follow; 

public interface FollowMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);
    
    int deleteByUserId1AndUserId2(Follow follow);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbggenerated
     */
    int insert(Follow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbggenerated
     */
    int insertSelective(Follow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbggenerated
     */
    Follow selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Follow record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table follow
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Follow record);

    /**
     * 根据userId1找寻所有关注者
     * @param id
     * @return 
     */
    List<Integer> selectByUserId1(Integer id);
    
    List<Integer> selectByUserId2(Integer id);

	Follow selectByUserId1AndUserId2(Integer id, Integer id2);

	Integer countByUserId1(Integer id);

	Integer countByUserId2(Integer id);
}